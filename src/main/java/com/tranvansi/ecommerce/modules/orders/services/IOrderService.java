package com.tranvansi.ecommerce.modules.orders.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.orders.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.responses.OrderResponse;

public interface IOrderService {
    @PreAuthorize("hasRole('USER')")
    OrderResponse createOrder(CreateOrderRequest request);
}
