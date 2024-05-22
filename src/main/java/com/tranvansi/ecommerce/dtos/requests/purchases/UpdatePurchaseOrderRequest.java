package com.tranvansi.ecommerce.dtos.requests.purchases;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePurchaseOrderRequest {
    private Integer status;

    @JsonProperty("payment_status")
    private Integer paymentStatus;
}
