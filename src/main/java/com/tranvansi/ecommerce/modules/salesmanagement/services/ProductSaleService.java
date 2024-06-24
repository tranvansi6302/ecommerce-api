package com.tranvansi.ecommerce.modules.salesmanagement.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.*;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IPricePlanService;
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

    @Override
    public Page<ProductSalesResponse> getAllProductSales(PageRequest pageRequest) {
        Page<ProductSale> productSales = productSaleRepository.findAll(pageRequest);
        Map<Integer, ProductSalesResponse> productSalesMap = new HashMap<>();

        for (ProductSale productSale : productSales) {
            Integer productId = productSale.getProduct().getId();
            List<ProductImage> productImages = productSale.getProduct().getProductImages();
            ProductSalesResponse productSalesResponse =
                    productSalesMap.getOrDefault(
                            productId,
                            ProductSalesResponse.builder()
                                    .productId(productId)
                                    .productName(productSale.getProduct().getName())
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
                                                    .toList())
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
        return new PageImpl<>(productSalesResponses, pageRequest, productSales.getTotalElements());
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
