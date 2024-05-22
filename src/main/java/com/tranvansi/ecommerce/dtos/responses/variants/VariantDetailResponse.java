package com.tranvansi.ecommerce.dtos.responses.variants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantDetailResponse {
    private String color;
    private String sku;
}
