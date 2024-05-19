package com.tranvansi.ecommerce.dtos.responses.variants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.dtos.responses.prices.OriginalPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.prices.PromotionPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantResponse {

    private Integer id;

    private String sku;

    private Integer sold;

    private SizeResponse size;

    private ColorResponse color;

    @JsonProperty("original_price")
    private OriginalPriceResponse originalPrice;

    @JsonProperty("promotion_price")
    private PromotionPriceResponse promotionPrice;
}
