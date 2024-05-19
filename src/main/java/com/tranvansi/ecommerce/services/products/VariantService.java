package com.tranvansi.ecommerce.services.products;

import java.util.ArrayList;
import java.util.List;

import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.products.VariantDetailRequest;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.VariantDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import com.tranvansi.ecommerce.entities.*;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.enums.ProductStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.VariantMapper;
import com.tranvansi.ecommerce.repositories.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariantService implements IVariantService {
    private final InventoryRepository inventoryRepository;
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final OriginalPriceRepository originalPriceRepository;
    private final PromotionPriceRepository promotionPriceRepository;
    private final VariantMapper variantMapper;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public VariantResponse createVariant(Integer productId, CreateVariantRequest request) {
        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Size size = findSizeById(request.getSizeId());

        List<VariantDetailResponse> variantDetailResponses = new ArrayList<>();
        for (VariantDetailRequest variantDetail : request.getVariantDetailRequests()) {
            Color color = findColorById(variantDetail.getColorId());
            String sku = variantDetail.getSku();
            if (variantRepository.existsBySku(sku)) {
                throw new AppException(ErrorCode.SKU_EXISTS);
            }


            // Save variant
            Variant savedVariant = saveVariant(product, size, color, sku,request);

            // Save inventory
            Inventory inventory = Inventory.builder().sku(sku).variant(savedVariant).build();
            inventoryRepository.save(inventory);

            OriginalPrice originalPrice =
                    variantMapper.createOriginalPrice(variantDetail.getOriginalPrice());
            originalPrice.setVariant(savedVariant);
            originalPriceRepository.save(originalPrice);

            VariantDetailResponse variantDetailResponse =
                    variantMapper.toVariantDetailResponse(savedVariant);
            variantDetailResponse.setOriginalPrice(
                    variantMapper.toOriginalPriceResponse(originalPrice));

            if (variantDetail.getPromotionPrice() != null) {
                PromotionPrice promotionPrice =
                        variantMapper.createPromotionPrice(variantDetail.getPromotionPrice());
                if (promotionPrice.getPrice() < originalPrice.getPrice()) {
                    throw new AppException(ErrorCode.PROMOTION_PRICE_GREATER_THAN_ORIGINAL_PRICE);
                }
                promotionPrice.setVariant(savedVariant);
                promotionPriceRepository.save(promotionPrice);
                variantDetailResponse.setPromotionPrice(
                        variantMapper.toPromotionPriceResponse(promotionPrice));
            }
            // Update pending update
            product.setPendingUpdate(ProductStatus.UPDATED.getValue());

            variantDetailResponses.add(variantDetailResponse);
        }

        return VariantResponse.builder()
                .size(size.getName())
                .variantDetailResponses(variantDetailResponses)
                .build();
    }

    @Override
    @Transactional
    public UpdateVariantResponse updateVariant(Integer variantId, UpdateVariantRequest request) {
        Variant variant =
                variantRepository
                        .findById(variantId)
                        .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        Color color = findColorById(request.getColorId());

        Size size = findSizeById(request.getSizeId());
        OriginalPrice originalPrice =
                originalPriceRepository
                        .findByVariantId(variantId)
                        .orElseThrow(() -> new AppException(ErrorCode.ORIGINAL_PRICE_NOT_FOUND));

        PromotionPrice promotionPrice =
                promotionPriceRepository.findByVariantId(variantId).orElse(null);


        if (promotionPrice != null) {
            if (request.getPromotionPriceRequest() == null) {
                promotionPriceRepository.delete(promotionPrice);
            } else {
                if (promotionPrice.getPrice() < originalPrice.getPrice()) {
                    throw new AppException(ErrorCode.PROMOTION_PRICE_GREATER_THAN_ORIGINAL_PRICE);
                }

                variantMapper.updatePromotionPrice(
                        promotionPrice, request.getPromotionPriceRequest());
                promotionPriceRepository.save(promotionPrice);
            }
        } else {
            if (request.getPromotionPriceRequest() != null) {
                PromotionPrice mapperPromotionPrice =
                        variantMapper.createPromotionPrice(request.getPromotionPriceRequest());
                mapperPromotionPrice.setVariant(variant);
                promotionPriceRepository.save(mapperPromotionPrice);
            }
        }

        variantMapper.updateVariant(variant, request);
        variant.setColor(color);
        variant.setSize(size);
        originalPrice.setPrice(request.getOriginalPriceRequest().getPrice());

        variantRepository.save(variant);
        originalPriceRepository.save(originalPrice);


        return UpdateVariantResponse.builder()
                .color(color.getName())
                .size(size.getName())
                .originalPriceResponse(variantMapper.toOriginalPriceResponse(originalPrice))
                .promotionPriceResponse(
                        promotionPrice == null
                                ? null
                                : variantMapper.toPromotionPriceResponse(promotionPrice))
                .build();

    }

    Size findSizeById(Integer sizeId) {
        return sizeRepository.findById(sizeId).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
    }

    Color findColorById(Integer colorId) {
        return colorRepository.findById(colorId).orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
    }

    Variant saveVariant(Product product, Size size, Color color, String sku, CreateVariantRequest request) {
        Variant variant = variantMapper.createVariant(request);
        variant.setProduct(product);
        variant.setSize(size);
        variant.setColor(color);
        variant.setSku(sku);
        variant.setSold(ProductStatus.DEFAULT_SOLD.getValue());
        return variantRepository.save(variant);
    }
}
