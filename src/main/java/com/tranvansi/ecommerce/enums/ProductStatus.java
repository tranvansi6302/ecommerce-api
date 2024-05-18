package com.tranvansi.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProductStatus {
    PENDING_UPDATE(0),
    UPDATED(1),
    SOFT_DELETED(1),
    NOT_DELETED(0),
    DEFAULT_SOLD(0);
    private Integer value;
}
