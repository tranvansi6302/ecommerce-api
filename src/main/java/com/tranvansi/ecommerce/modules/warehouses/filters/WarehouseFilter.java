package com.tranvansi.ecommerce.modules.warehouses.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseFilter {
    private String variantName;
    private String categorySlug;
    private String brandSlug;
    private String sku;
}
