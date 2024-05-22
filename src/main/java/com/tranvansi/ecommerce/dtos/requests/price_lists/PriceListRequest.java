package com.tranvansi.ecommerce.dtos.requests.price_lists;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListRequest {
    @JsonProperty("sale_price")
    private Double salePrice;

    @JsonProperty("variant_id")
    private Integer variantId;
}
