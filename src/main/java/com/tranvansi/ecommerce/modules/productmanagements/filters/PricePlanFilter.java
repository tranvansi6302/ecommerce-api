package com.tranvansi.ecommerce.modules.productmanagements.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePlanFilter {
    private String variantName;
    private String sku;
    private String categorySlug;
    private String brandSlug;
}
