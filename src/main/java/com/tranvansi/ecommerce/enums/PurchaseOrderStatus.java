package com.tranvansi.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PurchaseOrderStatus {
    PENDING(0),
    COMPLETED(1),
    CANCELLED(2);
    private Integer value;
}
