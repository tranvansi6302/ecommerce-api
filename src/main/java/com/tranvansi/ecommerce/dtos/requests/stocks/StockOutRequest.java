package com.tranvansi.ecommerce.dtos.requests.stocks;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockOutRequest {
    private Integer quantity;
    private Double pricing;

    @JsonProperty("variant_id")
    private Integer variantId;
}
