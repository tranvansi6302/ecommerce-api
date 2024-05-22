package com.tranvansi.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PurchaseOrderPaymentStatus {
    PENDING(0),
    PAID(1),
    DEBT(2);
    private Integer value;
}
