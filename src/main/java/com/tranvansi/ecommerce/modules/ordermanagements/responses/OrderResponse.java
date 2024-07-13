package com.tranvansi.ecommerce.modules.ordermanagements.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.OrderStatus;
import com.tranvansi.ecommerce.components.enums.PaidStatus;
import com.tranvansi.ecommerce.components.enums.PaymentMethodType;
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

    @JsonProperty("canceled_date")
    private LocalDateTime canceledDate;

    @JsonProperty("canceled_reason")
    private String canceledReason;

    @JsonProperty("pending_date")
    private LocalDateTime pendingDate;

    @JsonProperty("confirmed_date")
    private LocalDateTime confirmedDate;

    @JsonProperty("delivering_date")
    private LocalDateTime deliveringDate;

    @JsonProperty("delivered_date")
    private LocalDateTime deliveredDate;

    @JsonProperty("payment_method")
    private PaymentMethodType paymentMethod;

    @JsonProperty("paid_date")
    private LocalDateTime paidDate;

    @JsonProperty("online_payment_status")
    private PaidStatus onlinePaymentStatus;

    @JsonProperty("discount_order")
    private Double discountOrder;

    @JsonProperty("discount_shipping")
    private Double discountShipping;

    @JsonProperty("shipping_fee")
    private Double shippingFee;

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
