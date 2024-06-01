package com.tranvansi.ecommerce.modules.productmanagements.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.common.enums.PricePlanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePlanResponse {

    private Integer id;

    @JsonProperty("sale_price")
    private Double salePrice;

    @JsonProperty("promotion_price")
    private Double promotionPrice;

    private Double discount;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    private PricePlanStatus status;
}
