package com.tranvansi.ecommerce.modules.productmanagements.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePricePlanRequest {

    @JsonProperty("sale_price")
    //    @NotNull(message = "INVALID_SALE_PRICE_REQUIRED")
    private Double salePrice;

    @JsonProperty("promotion_price")
    //    @NotNull(message = "INVALID_PROMOTION_PRICE_REQUIRED")
    private Double promotionPrice;
}
