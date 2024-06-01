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
    @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
    @JsonProperty("variant_id")
    private Integer variantId;

    @JsonProperty("sale_price")
    private Double salePrice;

    @JsonProperty("promotion_price")
    private Double promotionPrice;

    private Double discount;

    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "INVALID_PRICE_PLAN_START_DATE_REQUIRED")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
