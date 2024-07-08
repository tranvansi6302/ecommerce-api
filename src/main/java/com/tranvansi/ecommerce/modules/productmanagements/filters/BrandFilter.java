package com.tranvansi.ecommerce.modules.productmanagements.filters;

import com.tranvansi.ecommerce.components.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandFilter {
    private String search;
    private BrandStatus status;
}
