package com.tranvansi.ecommerce.modules.orders.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.enums.OrderStatus;
import com.tranvansi.ecommerce.common.enums.RoleName;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import com.tranvansi.ecommerce.modules.carts.repositories.CartDetailRepository;
import com.tranvansi.ecommerce.modules.carts.repositories.CartRepository;
import com.tranvansi.ecommerce.modules.orders.entities.Order;
import com.tranvansi.ecommerce.modules.orders.entities.OrderDetail;
import com.tranvansi.ecommerce.modules.orders.mappers.OrderMapper;
import com.tranvansi.ecommerce.modules.orders.repositories.OrderDetailRepository;
import com.tranvansi.ecommerce.modules.orders.repositories.OrderRepository;
import com.tranvansi.ecommerce.modules.orders.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.orders.responses.OrderResponse;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final VariantRepository variantRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Order order =
                Order.builder()
                        .address(request.getAddress())
                        .orderDate(LocalDateTime.now())
                        .note(request.getNote())
                        .status(OrderStatus.PENDING)
                        .user(user)
                        .build();

        orderRepository.save(order);

        OrderResponse response = orderMapper.toOrderResponse(order);

        List<OrderResponse.OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        Cart cart =
                cartRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        for (CreateOrderRequest.OrderDetail orderDetailRequest : request.getOrderDetails()) {
            Variant variant =
                    variantRepository
                            .findById(orderDetailRequest.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

            CartDetail cartDetail =
                    cartDetailRepository
                            .findByVariantIdAndCart(variant.getId(), cart)
                            .orElseThrow(() -> new AppException(ErrorCode.CART_DETAIL_NOT_FOUND));

            OrderDetail orderDetail =
                    OrderDetail.builder()
                            .order(order)
                            .variant(cartDetail.getVariant())
                            .quantity(cartDetail.getQuantity())
                            .price(variant.getPricePlans().getFirst().getSalePrice())
                            .build();
            orderDetailRepository.save(orderDetail);
            cartDetailRepository.delete(cartDetail);

            OrderResponse.OrderDetailResponse orderDetailResponse =
                    orderMapper.toOrderDetailResponse(orderDetail);
            orderDetailResponses.add(orderDetailResponse);
        }
        response.setOrderDetails(orderDetailResponses);
        return response;
    }

    @Override
    public OrderResponse updateOrder(Integer orderId, UpdateOrderRequest request) {
        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String roleName =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (String.format("[%s_%s]", "ROLE", RoleName.USER.name()).equals(roleName)) {
            if (!order.getStatus().equals(OrderStatus.PENDING)) {
                throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
            }
        }

        // Logistic staff can only update order status to CONFIRMED or DELIVERED or CANCELLED
        orderMapper.updateOrder(order, request);
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }
}
