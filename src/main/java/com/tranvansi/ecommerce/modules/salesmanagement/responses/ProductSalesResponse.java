package com.tranvansi.ecommerce.modules.salesmanagement.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CategoryResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductImageResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.WarehousePurchasePriceAndAvailableQuantity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesResponse {
    private Integer id;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    private BrandResponse brand;
    private CategoryResponse category;

    @JsonProperty("total_sold")
    private Integer totalSold;

    @JsonProperty("average_rating")
    private Double averageRating;

    @JsonProperty("total_reviews")
    private Integer totalReviews;

    private List<ProductImageResponse> images;
    private List<VariantDetail> variants;

    private String sku;

    private String description;

    @JsonProperty("min_price")
    private Double minPrice;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VariantDetail {
        private Integer id;

        @JsonProperty("variant_name")
        private String variantName;

        private String sku;
        private String color;
        private String size;

        @JsonProperty("current_price_plan")
        private PricePlanResponse currentPricePlan;

        private WarehousePurchasePriceAndAvailableQuantity warehouse;
    }
}
