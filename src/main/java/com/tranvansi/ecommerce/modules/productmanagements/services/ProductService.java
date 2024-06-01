package com.tranvansi.ecommerce.modules.productmanagements.services;

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
import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Category;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Color;
import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Size;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.ColorMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.ProductMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.SizeMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ProductRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.SizeResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final IVariantService variantService;
    private final ICategoryService categoryService;
    private final IBrandService brandService;
    private final IColorService colorService;
    private final ISizeService sizeService;
    private final ProductMapper productMapper;
    private final VariantMapper variantMapper;
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

        Category category = categoryService.findCategoryById(request.getCategoryId());
        Brand brand = brandService.findBrandById(request.getBrandId());

        Product product = productMapper.createProduct(request);
        product.setCategory(category);
        product.setBrand(brand);

        Product savedProduct = productRepository.save(product);

        List<ColorResponse> colors = new ArrayList<>();
        List<SizeResponse> sizes = new ArrayList<>();
        List<VariantResponse> variantResponses = new ArrayList<>();

        for (Integer colorId : request.getColors()) {
            Color color = colorService.findColorById(colorId);

            ColorResponse colorResponse = colorMapper.toColorResponse(color);
            if (!colors.contains(colorResponse)) {
                colors.add(colorResponse);
            }

            for (Integer sizeId : request.getSizes()) {
                Size size = sizeService.findSizeById(sizeId);
                SizeResponse sizeResponse = sizeMapper.toSizeResponse(size);
                if (!sizes.contains(sizeResponse)) {
                    sizes.add(sizeResponse);
                }

                String sku = generateSKU(request.getSku(), size.getName(), color.getName());

                if (variantService.existsBySku(sku)) {
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
                                .productName(request.getName())
                                .size(size)
                                .sku(sku)
                                .build();
                Variant savedVariant = variantService.saveVariant(variant);

                VariantResponse variantResponse = variantMapper.toVariantResponse(savedVariant);
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
        Product product = findProductById(id);
        if (productRepository.existsByName(request.getName())
                && !product.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        Category category = categoryService.findCategoryById(request.getCategoryId());

        Brand brand = brandService.findBrandById(request.getBrandId());

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

    @Override
    public ProductDetailResponse getProductById(Integer id) {
        Product product = findProductById(id);
        ProductDetailResponse response = productMapper.toProductDetailResponse(product);
        response.getVariants()
                .forEach(
                        variantDetail -> {
                            PricePlan currentPricePlan = getCurrentPricePlan(variantDetail.getId());
                            if (currentPricePlan != null) {
                                variantDetail.setCurrentPricePlan(
                                        pricePlanMapper.toPricePlanResponse(currentPricePlan));
                            }
                        });
        return response;
    }

    @Override
    public Product findProductById(Integer id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
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
