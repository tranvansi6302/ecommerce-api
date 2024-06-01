package com.tranvansi.ecommerce.modules.ordermanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.enums.OrderStatus;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.filters.OrderFilter;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.OrderResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IOrderService;
import com.tranvansi.ecommerce.modules.ordermanagements.specifications.OrderSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "status", required = false) OrderStatus status,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        OrderFilter filter = OrderFilter.builder().status(status).search(search).build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<OrderResponse> orderResponses =
                orderService.getAllOrders(pageRequest, new OrderSpecification(filter));
        PagedResponse<List<OrderResponse>> response =
                BuildResponse.buildPagedResponse(orderResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Integer orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder().result(orderResponse).build();
        return ResponseEntity.ok(response);
    }

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
