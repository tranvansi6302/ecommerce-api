package com.tranvansi.ecommerce.modules.salesmanagement.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CategoryResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductImageResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesResponse {
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VariantDetail {
        private Integer id;
        private String variantName;
        private String sku;
        private String color;
        private String size;

        private PricePlanResponse currentPricePlan;
    }
}
