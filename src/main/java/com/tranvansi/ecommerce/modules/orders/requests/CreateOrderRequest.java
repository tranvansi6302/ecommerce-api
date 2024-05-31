package com.tranvansi.ecommerce.modules.orders.requests;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Pattern(regexp = "\\d{10}", message = "INVALID_ORDER_PHONE_NUMBER_FORMAT")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("order_details")
    @NotNull(message = "INVALID_ORDER_DETAILS")
    private List<OrderDetail> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetail {
        @JsonProperty("variant_id")
        @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
        private Integer variantId;
    }
}
