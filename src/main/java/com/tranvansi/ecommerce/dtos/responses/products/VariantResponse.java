package com.tranvansi.ecommerce.dtos.responses.products;

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
public class VariantResponse {
    private String size;

    @JsonProperty("variant_details")
    private List<VariantDetailResponse> variantDetailResponses;
}
