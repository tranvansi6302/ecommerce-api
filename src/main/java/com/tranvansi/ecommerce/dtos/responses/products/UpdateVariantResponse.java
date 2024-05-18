package com.tranvansi.ecommerce.dtos.responses.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateVariantResponse {
    private String size;
    private String color;

    @JsonProperty("original_price")
    private OriginalPriceResponse originalPriceResponse;

    @JsonProperty("promotion_price")
    private PromotionPriceResponse promotionPriceResponse;
}
