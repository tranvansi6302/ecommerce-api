package com.tranvansi.ecommerce.modules.orders.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.common.enums.OrderStatus;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.users.responses.ProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;
    private String address;
    private String note;
    private OrderStatus status;
    private ProfileResponse user;

    @JsonProperty("order_details")
    List<OrderDetailResponse> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetailResponse {
        private Integer id;
        private Integer quantity;
        private Double price;
        private VariantResponse variant;
    }
}
