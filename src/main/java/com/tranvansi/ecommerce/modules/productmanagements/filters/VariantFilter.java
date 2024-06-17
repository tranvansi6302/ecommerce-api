package com.tranvansi.ecommerce.modules.productmanagements.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantFilter {
    private String search;
    private String brandSlug;
    private String categorySlug;

}
