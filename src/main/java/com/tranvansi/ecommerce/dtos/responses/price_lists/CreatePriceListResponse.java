package com.tranvansi.ecommerce.dtos.responses.price_lists;

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
public class CreatePriceListResponse {
    @JsonProperty("price_lists")
    private List<PriceListResponse> priceLists;
}
