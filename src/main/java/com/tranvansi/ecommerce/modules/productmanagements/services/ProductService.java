package com.tranvansi.ecommerce.modules.productmanagements.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.components.constants.FileConstant;
import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.services.AmazonClientService;
import com.tranvansi.ecommerce.components.utils.ConvertUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.*;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.ProductMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ProductRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UploadProductImagesRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.*;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final IProductImageService productImageService;
    private final IVariantService variantService;
    private final ICategoryService categoryService;
    private final IBrandService brandService;
    private final ProductMapper productMapper;
    private final VariantMapper variantMapper;

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;

    private final PricePlanRepository pricePlanRepository;
    private final PricePlanMapper pricePlanMapper;
    private final AmazonClientService amazonClientService;

    @Override
    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        if (productRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.SKU_ALREADY_EXISTS);
        }

        Category category = categoryService.findCategoryById(request.getCategoryId());
        Brand brand = brandService.findBrandById(request.getBrandId());

        Product product = productMapper.createProduct(request);
        product.setCategory(category);
        product.setBrand(brand);

        Product savedProduct = productRepository.save(product);

        List<VariantResponse> variantResponses = new ArrayList<>();

        for (String color : request.getColors())
            for (String size : request.getSizes()) {

                String sku = generateSKU(request.getSku(), size, color);

                Variant variant =
                        Variant.builder()
                                .product(savedProduct)
                                .variantName(
                                        String.format(
                                                "%s - %s - %s", request.getName(), size, color))
                                .color(color)
                                .productName(request.getName())
                                .size(size)
                                .sku(sku)
                                .build();
                Variant savedVariant = variantService.saveVariant(variant);

                VariantResponse variantResponse = variantMapper.toVariantResponse(savedVariant);
                variantResponses.add(variantResponse);
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
        return String.format("%s-%s-%s", sku, sizeName, ConvertUtil.toRemoveAccent(colorName));
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
        return productMapper.toCreateProductResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse uploadImageProducts(Integer id, UploadProductImagesRequest request) {
        List<ProductImageResponse> imageResponses = new ArrayList<>();
        Product product = findProductById(id);
        for (int i = 0; i < request.getFiles().size(); i++) {
            if (productImageService.countProductImageByProductId(id) >= FileConstant.FILE_LIMIT) {
                throw new AppException(ErrorCode.PRODUCT_IMAGE_LIMIT_EXCEEDED);
            }
            String imageUrl = "";
            imageUrl = amazonClientService.uploadFile(request.getFiles().get(i));
            ProductImage productImage =
                    ProductImage.builder().product(product).url(imageUrl).build();
            productImageService.saveProductImage(productImage);
            imageResponses.add(ProductImageResponse.builder().url(imageUrl).build());
        }
        ProductResponse response = productMapper.toProductResponse(product);
        response.setProductImages(imageResponses);
        return response;
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
