package com.tranvansi.ecommerce.dtos.responses.purchases;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePurchaseOrderDetailResponse {
    @JsonProperty("purchase_order_id")
    private Integer purchaseOrderId;

    @JsonProperty("purchase_order_details")
    List<PurchaseOrderDetailResponse> purchaseOrderDetails;
}
