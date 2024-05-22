package com.tranvansi.ecommerce.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilter {
    // CategoryId
    private Integer category;

    // BrandId
    private Integer brand;

    private Boolean sortPrice;
}
