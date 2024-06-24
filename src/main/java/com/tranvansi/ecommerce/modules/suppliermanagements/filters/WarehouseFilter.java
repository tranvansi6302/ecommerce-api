package com.tranvansi.ecommerce.modules.suppliermanagements.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseFilter {
    private String search;
    private String categorySlug;
    private String brandSlug;
}
