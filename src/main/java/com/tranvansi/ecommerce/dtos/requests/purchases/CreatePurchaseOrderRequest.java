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
public class CreatePurchaseOrderRequest {
    @JsonProperty("supplier_id")
    private Integer supplierId;

    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    private String note;

    private Integer status;
}
