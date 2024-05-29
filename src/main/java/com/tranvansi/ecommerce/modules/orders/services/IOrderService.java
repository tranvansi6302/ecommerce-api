package com.tranvansi.ecommerce.modules.orders.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.orders.entities.Order;
import com.tranvansi.ecommerce.modules.orders.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.responses.OrderResponse;

public interface IOrderService {
    @PreAuthorize("hasRole('USER')")
    OrderResponse createOrder(CreateOrderRequest request);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    OrderResponse updateOrder(Integer orderId, UpdateOrderRequest request);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    Page<OrderResponse> getAllOrders(PageRequest pageRequest, Specification<Order> specification);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    OrderResponse getOrderById(Integer orderId);
}
