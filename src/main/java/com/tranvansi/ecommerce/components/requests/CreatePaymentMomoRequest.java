package com.tranvansi.ecommerce.components.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentMomoRequest {
    @NotBlank(message = "INVALID_ORDER_CODE_REQUIRED")
    @JsonProperty("order_id")
    private String orderId;

    @NotNull(message = "INVALID_AMOUNT_REQUIRED")
    private Long amount;
}
