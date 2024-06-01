package com.tranvansi.ecommerce.modules.productmanagements.responses;

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

    @JsonProperty("average_rating")
    private Double averageRating;

    private Integer sold;

    private BrandResponse brand;
    private CategoryResponse category;
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

        private String sku;
        private ColorResponse color;
        private SizeResponse size;
        private WarehousePurchasePriceAndAvailableQuantity warehouse;

        @JsonProperty("current_price_plan")
        private PricePlanResponse currentPricePlan;
    }
}
