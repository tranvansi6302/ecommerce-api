package com.tranvansi.ecommerce.modules.salesmanagement.services;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.*;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IPricePlanService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IProductService;
import com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces.IReviewService;
import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import com.tranvansi.ecommerce.modules.salesmanagement.mappers.ProductSaleMapper;
import com.tranvansi.ecommerce.modules.salesmanagement.repositories.ProductSaleRepository;
import com.tranvansi.ecommerce.modules.salesmanagement.responses.ProductSalesResponse;
import com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces.IProductSaleService;
import com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces.ISaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSaleService implements IProductSaleService {
    private final ProductSaleRepository productSaleRepository;
    private final ProductMapper productMapper;
    private final ProductSaleMapper productSaleMapper;
    private final PricePlanMapper pricePlanMapper;

    private final IPricePlanService pricePlanService;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    private final IReviewService reviewService;
    private final ISaleService saleService;

    private final IProductService productService;

    @Override
    public Page<ProductSalesResponse> getAllProductSales(
            PageRequest pageRequest, Specification<ProductSale> specification) {
        // Get all product sales no pagination
        List<ProductSale> allProductSales = productSaleRepository.findAll(specification);
        Map<Integer, ProductSalesResponse> productSalesMap = new HashMap<>();

        for (ProductSale productSale : allProductSales) {
            Integer productId = productSale.getProduct().getId();
            List<ProductImage> productImages = productSale.getProduct().getProductImages();
            ProductSalesResponse productSalesResponse =
                    productSalesMap.getOrDefault(
                            productId,
                            ProductSalesResponse.builder()
                                    .id(productSale.getId())
                                    .productId(productId)
                                    .productName(productSale.getProduct().getName())
                                    .minPrice(getMinPrice(productSale.getProduct()))
                                    .createdAt(productSale.getCreatedAt())
                                    .updatedAt(productSale.getUpdatedAt())
                                    .brand(
                                            brandMapper.toBrandResponse(
                                                    productSale.getProduct().getBrand()))
                                    .category(
                                            categoryMapper.toCategoryResponse(
                                                    productSale.getProduct().getCategory()))
                                    .totalSold(getTotalSoldProduct(productSale.getProduct()))
                                    .averageRating(getAverageRating(productSale.getProduct()))
                                    .totalReviews(getTotalReviews(productSale.getProduct()))
                                    .images(
                                            productImages.stream()
                                                    .map(productMapper::toProductImageResponses)
                                                    .collect(Collectors.toList()))
                                    .variants(new ArrayList<>())
                                    .build());

            ProductSalesResponse.VariantDetail variantDetail =
                    productSaleMapper.toVariantDetail(productSale);
            PricePlan currentPricePlan = getCurrentPricePlan(productSale.getVariant().getId());
            if (currentPricePlan != null) {
                variantDetail.setCurrentPricePlan(
                        pricePlanMapper.toPricePlanResponse(currentPricePlan));
            }
            productSalesResponse.getVariants().add(variantDetail);
            productSalesMap.put(productId, productSalesResponse);
        }

        List<ProductSalesResponse> productSalesResponses =
                new ArrayList<>(productSalesMap.values());

        // Pagination
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), productSalesResponses.size());
        List<ProductSalesResponse> pagedResponses = productSalesResponses.subList(start, end);

        return new PageImpl<>(pagedResponses, pageRequest, productSalesResponses.size());
    }

    @Override
    public ProductSalesResponse getProductSaleByProductId(Integer productId) {

        Product product = productService.findProductById(productId);
        if (product == null) {
            return null;
        }

        // Find all product sales by product
        List<ProductSale> productSales = productSaleRepository.findAllByProduct(product);

        // Init ProductSalesResponse
        ProductSalesResponse productSalesResponse =
                ProductSalesResponse.builder()
                        .id(product.getId())
                        .productId(productId)
                        .productName(product.getName())
                        .minPrice(getMinPrice(product))
                        .createdAt(product.getCreatedAt())
                        .updatedAt(product.getUpdatedAt())
                        .brand(brandMapper.toBrandResponse(product.getBrand()))
                        .category(categoryMapper.toCategoryResponse(product.getCategory()))
                        .description(product.getDescription())
                        .totalSold(getTotalSoldProduct(product))
                        .averageRating(getAverageRating(product))
                        .totalReviews(getTotalReviews(product))
                        .images(
                                product.getProductImages().stream()
                                        .map(productMapper::toProductImageResponses)
                                        .collect(Collectors.toList()))
                        .variants(new ArrayList<>())
                        .build();

        // Add variant detail to ProductSalesResponse
        for (ProductSale productSale : productSales) {
            ProductSalesResponse.VariantDetail variantDetail =
                    productSaleMapper.toVariantDetail(productSale);
            PricePlan currentPricePlan = getCurrentPricePlan(productSale.getVariant().getId());
            if (currentPricePlan != null) {
                variantDetail.setCurrentPricePlan(
                        pricePlanMapper.toPricePlanResponse(currentPricePlan));
            }
            productSalesResponse.getVariants().add(variantDetail);
        }

        return productSalesResponse;
    }

    private Double getMinPrice(Product product) {
        List<Variant> variants = product.getVariants();
        if (variants == null || variants.isEmpty()) {
            return 0.0;
        }
        return variants.stream()
                .map(Variant::getId)
                .map(this::getCurrentPricePlan)
                .filter(Objects::nonNull)
                .map(
                        pricePlan ->
                                Optional.ofNullable(pricePlan.getPromotionPrice())
                                        .orElse(pricePlan.getSalePrice()))
                .min(Double::compare)
                .orElse(0.0);
    }

    private Double getAverageRating(Product product) {
        Integer totalStars = reviewService.findTotalStarsByProductId(product.getId());
        Integer reviewCount = reviewService.findReviewCountByProductId(product.getId());
        if (totalStars == null || reviewCount == null || reviewCount == 0) {
            return 0.0;
        }
        return totalStars.doubleValue() / reviewCount;
    }

    private Integer getTotalReviews(Product product) {
        Integer reviewCount = reviewService.findReviewCountByProductId(product.getId());
        if (reviewCount == null) {
            return 0;
        }
        return reviewCount;
    }

    private Integer getTotalSoldProduct(Product product) {
        Integer totalSold = saleService.findTotalSoldByProductId(product.getId());
        if (totalSold == null) {
            return 0;
        }
        return totalSold;
    }

    private boolean isValidPricePlan(PricePlan pricePlan) {
        LocalDateTime now = LocalDateTime.now();
        return pricePlan.getStartDate().isBefore(now)
                && (pricePlan.getEndDate() == null || pricePlan.getEndDate().isAfter(now));
    }

    private PricePlan getCurrentPricePlan(Integer variantId) {
        List<PricePlan> pricePlans =
                pricePlanService.findByVariantIdOrderByStartDateDesc(variantId);
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
