package com.tranvansi.ecommerce.dtos.requests.products;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantDetailRequest {
    @NotNull(message = "INVALID_COLOR_ID_REQUIRED")
    @JsonProperty("color_id")
    private Integer colorId;

    private Integer sold;

    @NotBlank(message = "INVALID_SKU_REQUIRED")
    @Size(min = 6, max = 6, message = "INVALID_SKU_LENGTH")
    private String sku;

    @NotNull(message = "INVALID_ORIGINAL_PRICE_REQUIRED")
    @JsonProperty("original_price")
    private @Valid OriginalPriceRequest originalPrice;

    @JsonProperty("promotion_price")
    private @Valid PromotionPriceRequest promotionPrice;
}
