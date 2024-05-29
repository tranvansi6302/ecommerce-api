package com.tranvansi.ecommerce.modules.orders.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private String address;
    private String note;

    @JsonProperty("order_details")
    @NotNull(message = "INVALID_ORDER_DETAILS")
    private List<OrderDetail> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetail {
        @JsonProperty("variant_id")
        @NotNull(message = "INVALID_VARIANT_ID")
        private Integer variantId;
    }
}
