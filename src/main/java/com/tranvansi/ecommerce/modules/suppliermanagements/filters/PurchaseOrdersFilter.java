package com.tranvansi.ecommerce.modules.suppliermanagements.filters;

import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrdersFilter {
    private PurchaseOrderStatus status;
    private String search;
    private Integer supplierId;
}
