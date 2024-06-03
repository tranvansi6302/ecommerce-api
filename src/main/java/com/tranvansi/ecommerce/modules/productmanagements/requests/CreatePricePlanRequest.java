package com.tranvansi.ecommerce.modules.productmanagements.requests;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.PricePlanStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePricePlanRequest {

    @JsonProperty("price_plans")
    @NotNull(message = "INVALID_PRICE_PLAN_REQUIRED")
    private List<PricePlanRequest> pricePlans;

    @Data
    public static class PricePlanRequest {
        @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
        @JsonProperty("variant_id")
        private Integer variantId;

        @JsonProperty("sale_price")
        private Double salePrice;

        @JsonProperty("promotion_price")
        private Double promotionPrice;

        private Double discount;
        private PricePlanStatus status;

        @JsonProperty("start_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @NotBlank(message = "INVALID_PRICE_PLAN_START_DATE_REQUIRED")
        private LocalDateTime startDate;

        @JsonProperty("end_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endDate;
    }
}
