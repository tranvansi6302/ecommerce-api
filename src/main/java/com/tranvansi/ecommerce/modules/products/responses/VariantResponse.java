package com.tranvansi.ecommerce.modules.products.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.sizes.responses.SizeResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantResponse {
    private Integer id;

    @JsonProperty("variant_name")
    private String variantName;

    private String sku;

    private ColorResponse color;

    private SizeResponse size;
}
