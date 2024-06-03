package com.tranvansi.ecommerce.modules.ordermanagements.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.OrderStatus;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ProfileResponse;

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

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("order_code")
    private String orderCode;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

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
