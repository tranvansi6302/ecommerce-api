package com.tranvansi.ecommerce.dtos.requests.variants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.dtos.requests.prices.OriginalPriceRequest;
import com.tranvansi.ecommerce.dtos.requests.prices.PromotionPriceRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVariantRequest {
    @NotNull(message = "INVALID_COLOR_ID_REQUIRED")
    @JsonProperty("color_id")
    private Integer colorId;

    @NotNull(message = "INVALID_SIZE_ID_REQUIRED")
    @JsonProperty("size_id")
    private Integer sizeId;

    @NotNull(message = "INVALID_ORIGINAL_PRICE_REQUIRED")
    @JsonProperty("original_price")
    private @Valid OriginalPriceRequest originalPriceRequest;

    @JsonProperty("promotion_price")
    private @Valid PromotionPriceRequest promotionPriceRequest;
}
