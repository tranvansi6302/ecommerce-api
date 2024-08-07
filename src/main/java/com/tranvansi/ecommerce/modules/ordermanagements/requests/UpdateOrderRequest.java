package com.tranvansi.ecommerce.modules.ordermanagements.requests;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderRequest {
    @NotNull(message = "INVALID_ORDER_STATUS")
    private OrderStatus status;

    @JsonProperty("tracking_code")
    private String trackingCode;

    @JsonProperty("canceled_reason")
    private String canceledReason;
}
