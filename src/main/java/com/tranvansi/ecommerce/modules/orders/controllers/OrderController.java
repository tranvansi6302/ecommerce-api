package com.tranvansi.ecommerce.modules.orders.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.orders.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.responses.OrderResponse;
import com.tranvansi.ecommerce.modules.orders.services.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestBody @Valid CreateOrderRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .result(orderResponse)
                        .message(Message.ORDER_CREATED_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable Integer orderId, @RequestBody @Valid UpdateOrderRequest request) {
        OrderResponse orderResponse = orderService.updateOrder(orderId, request);
        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .result(orderResponse)
                        .message(Message.ORDER_UPDATED_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}