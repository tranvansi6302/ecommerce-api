package com.tranvansi.ecommerce.modules.suppliermanagements.filters;

import com.tranvansi.ecommerce.components.enums.SupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierFilter {
    private String search;
    private SupplierStatus status;
}
