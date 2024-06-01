package com.tranvansi.ecommerce.modules.ordermanagements.filters;

import com.tranvansi.ecommerce.common.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFilter {
    private OrderStatus status;
    private String search;
}
