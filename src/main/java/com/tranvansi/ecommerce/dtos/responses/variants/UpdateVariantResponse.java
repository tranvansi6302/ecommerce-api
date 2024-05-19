package com.tranvansi.ecommerce.dtos.responses.variants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.dtos.responses.prices.OriginalPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.prices.PromotionPriceResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVariantResponse {
    private String size;
    private String color;

    @JsonProperty("original_price")
    private OriginalPriceResponse originalPriceResponse;

    @JsonProperty("promotion_price")
    private PromotionPriceResponse promotionPriceResponse;
}
