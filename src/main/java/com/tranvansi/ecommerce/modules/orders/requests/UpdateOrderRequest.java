package com.tranvansi.ecommerce.modules.orders.requests;

import jakarta.validation.constraints.NotNull;

import com.tranvansi.ecommerce.common.enums.OrderStatus;

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
}
