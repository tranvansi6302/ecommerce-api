package com.tranvansi.ecommerce.modules.products.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilter {
    private String productName;
    private String categorySlug;
    private String brandSlug;
    private Double priceMin;
    private Double priceMax;
    // Filter rating, sold ...
}
