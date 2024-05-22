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
public class PurchaseDetailRequest {
    @JsonProperty("variant_id")
    private Integer variantId;

    private Integer quantity;

    @JsonProperty("purchase_price")
    private Double purchasePrice;
}
