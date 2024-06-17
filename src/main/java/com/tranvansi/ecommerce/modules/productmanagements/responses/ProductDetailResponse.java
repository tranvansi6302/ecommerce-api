package com.tranvansi.ecommerce.modules.productmanagements.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponse {
    private Integer id;
    private String name;
    private String description;

    private String sku;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("average_rating")
    private Double averageRating;

    private Integer sold;

    private BrandResponse brand;
    private CategoryResponse category;
    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;
    private List<VariantDetail> variants;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VariantDetail {
        private Integer id;

        @JsonProperty("variant_name")
        private String variantName;

        @JsonProperty("product_name")
        private String productName;

        @JsonProperty("product_id")
        private Integer productId;

        private String sku;

        private String color;
        private String size;

        private WarehousePurchasePriceAndAvailableQuantity warehouse;

        @JsonProperty("current_price_plan")
        private PricePlanResponse currentPricePlan;
    }
}
