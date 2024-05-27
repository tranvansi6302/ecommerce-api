package com.tranvansi.ecommerce.modules.products.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.brands.entities.Brand;
import com.tranvansi.ecommerce.modules.brands.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.brands.repositories.BrandRepository;
import com.tranvansi.ecommerce.modules.categories.entities.Category;
import com.tranvansi.ecommerce.modules.categories.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.categories.repositories.CategoryRepository;
import com.tranvansi.ecommerce.modules.colors.entities.Color;
import com.tranvansi.ecommerce.modules.colors.mappers.ColorMapper;
import com.tranvansi.ecommerce.modules.colors.repositories.ColorRepository;
import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.pricePlans.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.mappers.ProductMapper;
import com.tranvansi.ecommerce.modules.products.repositories.ProductRepository;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.sizes.entities.Size;
import com.tranvansi.ecommerce.modules.sizes.mappers.SizeMapper;
import com.tranvansi.ecommerce.modules.sizes.repositories.SizeRepository;
import com.tranvansi.ecommerce.modules.sizes.responses.SizeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductMapper productMapper;
    private final SizeMapper sizeMapper;
    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final ColorMapper colorMapper;
    private final PricePlanRepository pricePlanRepository;
    private final PricePlanMapper pricePlanMapper;

    @Override
    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        Category category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Brand brand =
                brandRepository
                        .findById(request.getBrandId())
                        .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        Product product = productMapper.createProduct(request);
        product.setCategory(category);
        product.setBrand(brand);

        Product savedProduct = productRepository.save(product);
        List<Variant> variants = new ArrayList<>();
        List<ColorResponse> colors = new ArrayList<>();
        List<SizeResponse> sizes = new ArrayList<>();
        List<VariantResponse> variantResponses = new ArrayList<>();

        for (Integer colorId : request.getColors()) {
            Color color =
                    colorRepository
                            .findById(colorId)
                            .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

            ColorResponse colorResponse = colorMapper.toColorResponse(color);
            if (!colors.contains(colorResponse)) {
                colors.add(colorResponse);
            }

            for (Integer sizeId : request.getSizes()) {
                Size size =
                        sizeRepository
                                .findById(sizeId)
                                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
                SizeResponse sizeResponse = sizeMapper.toSizeResponse(size);
                if (!sizes.contains(sizeResponse)) {
                    sizes.add(sizeResponse);
                }

                String sku = generateSKU(request.getSku(), size.getName(), color.getName());

                if (variantRepository.existsBySku(sku)) {
                    throw new AppException(ErrorCode.SKU_ALREADY_EXISTS);
                }

                Variant variant =
                        Variant.builder()
                                .product(savedProduct)
                                .variantName(
                                        String.format(
                                                "%s - %s - %s",
                                                request.getName(), size.getName(), color.getName()))
                                .color(color)
                                .size(size)
                                .sku(sku)
                                .build();
                Variant savedVariant = variantRepository.save(variant);

                VariantResponse variantResponse =
                        VariantResponse.builder()
                                .id(savedVariant.getId())
                                .sku(sku)
                                .color(colorResponse)
                                .variantName(
                                        String.format(
                                                "%s - %s - %s",
                                                request.getName(), size.getName(), color.getName()))
                                .size(sizeResponse)
                                .build();
                variantResponses.add(variantResponse);
            }
        }

        return CreateProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .category(categoryMapper.toCategoryResponse(savedProduct.getCategory()))
                .brand(brandMapper.toBrandResponse(savedProduct.getBrand()))
                .variants(variantResponses)
                .build();
    }

    private String generateSKU(String sku, String sizeName, String colorName) {
        String colorInitial = colorName.substring(0, 1).toUpperCase();
        return String.format("%s-%s-%s", sku, sizeName, colorInitial);
    }

    @Override
    public CreateProductResponse updateProduct(Integer id, UpdateProductRequest request) {
        Product product =
                productRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (productRepository.existsByName(request.getName())
                && !product.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        Category category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Brand brand =
                brandRepository
                        .findById(request.getBrandId())
                        .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        productMapper.updateProduct(product, request);
        product.setCategory(category);
        product.setBrand(brand);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public Page<ProductDetailResponse> getAllProducts(
            PageRequest pageRequest, Specification<Product> specification) {
        Page<Product> products = productRepository.findAll(specification, pageRequest);

        return products.map(
                product -> {
                    ProductDetailResponse response = productMapper.toProductDetailResponse(product);
                    response.getVariants()
                            .forEach(
                                    variantDetail -> {
                                        PricePlan currentPricePlan =
                                                getCurrentPricePlan(variantDetail.getId());
                                        if (currentPricePlan != null) {
                                            variantDetail.setCurrentPricePlan(
                                                    pricePlanMapper.toPricePlanResponse(
                                                            currentPricePlan));
                                        }
                                    });
                    return response;
                });
    }

    private PricePlan getCurrentPricePlan(Integer variantId) {
        List<PricePlan> pricePlans =
                pricePlanRepository.findByVariantIdOrderByStartDateDesc(variantId);
        LocalDateTime now = LocalDateTime.now();

        for (PricePlan pricePlan : pricePlans) {
            if (pricePlan.getStartDate().isBefore(now)
                    && (pricePlan.getEndDate() == null || pricePlan.getEndDate().isAfter(now))) {
                return pricePlan;
            }
        }
        return null;
    }
}
