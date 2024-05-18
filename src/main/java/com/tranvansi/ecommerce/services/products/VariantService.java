package com.tranvansi.ecommerce.services.products;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.products.VariantDetailRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(
                () -> new AppException(ErrorCode.SIZE_NOT_FOUND)
        );

        List<VariantDetailResponse> variantDetailResponses = new ArrayList<>();
        for (VariantDetailRequest variantDetail : request.getVariantDetailRequests()) {
            Color color = colorRepository.findById(variantDetail.getColorId()).orElseThrow(
                    () -> new AppException(ErrorCode.COLOR_NOT_FOUND)
            );

            String sku = variantDetail.getSku();
            if (variantRepository.existsBySku(sku)) {
                throw new AppException(ErrorCode.SKU_EXISTS);
            }

            Variant variant = variantMapper.toVariant(request);
            variant.setProduct(product);
            variant.setSize(size);
            variant.setColor(color);
            variant.setSku(sku);
            variant.setSold(ProductStatus.DEFAULT_SOLD.getValue());


            Variant savedVariant = variantRepository.save(variant);

            Inventory inventory = Inventory.builder()
                    .sku(sku)
                    .variant(savedVariant)
                    .build();
            inventoryRepository.save(inventory);

            OriginalPrice originalPrice = variantMapper.toOriginalPrice(variantDetail.getOriginalPrice());
            originalPrice.setVariant(savedVariant);
            originalPriceRepository.save(originalPrice);

            VariantDetailResponse variantDetailResponse = variantMapper.toVariantDetailResponse(savedVariant);
            variantDetailResponse.setOriginalPrice(variantMapper.toOriginalPriceResponse(originalPrice));

            if (variantDetail.getPromotionPrice() != null) {
                PromotionPrice promotionPrice = variantMapper.toPromotionPrice(variantDetail.getPromotionPrice());
                if (promotionPrice.getPrice() >= originalPrice.getPrice()) {
                    throw new AppException(ErrorCode.PROMOTION_PRICE_GREATER_THAN_ORIGINAL_PRICE);
                }
                promotionPrice.setVariant(savedVariant);
                promotionPriceRepository.save(promotionPrice);
                variantDetailResponse.setPromotionPrice(variantMapper.toPromotionPriceResponse(promotionPrice));
            }

            variantDetailResponses.add(variantDetailResponse);
        }

        return VariantResponse.builder()
                .size(size.getName())
                .variantDetailResponses(variantDetailResponses)
                .build();
    }
}
