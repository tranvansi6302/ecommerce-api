package com.tranvansi.ecommerce.modules.productmanagements.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
