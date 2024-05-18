package com.tranvansi.ecommerce.dtos.responses.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantDetailResponse {
    private String color;
    private String sku;

    @JsonProperty("original_price")
    private OriginalPriceResponse originalPrice;

    @JsonProperty("promotion_price")
    private PromotionPriceResponse promotionPrice;
}
