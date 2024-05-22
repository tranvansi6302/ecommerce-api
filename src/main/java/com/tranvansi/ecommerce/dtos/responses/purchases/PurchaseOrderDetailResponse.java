package com.tranvansi.ecommerce.dtos.responses.purchases;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDetailResponse {
    @JsonProperty("variant_id")
    private Integer variantId;

    private Integer quantity;

    @JsonProperty("purchase_price")
    private Double purchasePrice;
}
