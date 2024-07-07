package com.tranvansi.ecommerce.modules.ordermanagements.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.OrderStatus;
import com.tranvansi.ecommerce.components.enums.RoleName;
import com.tranvansi.ecommerce.components.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.CartDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.OrderDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.mappers.OrderMapper;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.OrderRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.CreateOrderRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.OrderResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.ICartDetailService;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.ICartService;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IOrderDetailService;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IOrderService;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.salesmanagement.entities.Sale;
import com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces.ISaleService;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IWarehouseService;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final IOrderDetailService orderDetailService;
    private final IVariantService variantService;
    private final AuthUtil authUtil;
    private final ICartService cartService;
    private final ICartDetailService cartDetailService;
    private final IWarehouseService warehouseService;
    private final ISaleService saleService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        User user = authUtil.getUser();
        UUID uuid = UUID.randomUUID();
        String orderCode = uuid.toString().substring(0, 8);
        Order order =
                Order.builder()
                        .address(request.getAddress())
                        .orderDate(LocalDateTime.now())
                        .note(request.getNote())
                        .phoneNumber(request.getPhoneNumber())
                        .status(OrderStatus.PENDING)
                        .orderCode(orderCode.toUpperCase())
                        .user(user)
                        .build();

        orderRepository.save(order);

        OrderResponse response = orderMapper.toOrderResponse(order);

        List<OrderResponse.OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        Cart cart = cartService.findByUserId(user.getId());

        for (CreateOrderRequest.OrderDetail orderDetailRequest : request.getOrderDetails()) {
            Variant variant = variantService.findVariantById(orderDetailRequest.getVariantId());

            CartDetail cartDetail =
                    cartDetailService.findCartDetailByVariantIdAndCart(variant.getId(), cart);

            OrderDetail orderDetail =
                    OrderDetail.builder()
                            .order(order)
                            .variant(cartDetail.getVariant())
                            .quantity(cartDetail.getQuantity())
                            .price(variant.getPricePlans().getFirst().getSalePrice())
                            .build();
            orderDetailService.saveOrderDetail(orderDetail);
            cartDetailService.deleteCartDetail(cartDetail);

            OrderResponse.OrderDetailResponse orderDetailResponse =
                    orderMapper.toOrderDetailResponse(orderDetail);
            orderDetailResponses.add(orderDetailResponse);
        }
        response.setOrderDetails(orderDetailResponses);
        return response;
    }

    @Override
    public OrderResponse updateOrder(Integer orderId, UpdateOrderRequest request) {
        User user = authUtil.getUser();
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
            if (!Objects.equals(order.getUser().getId(), user.getId())) {
                throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
            }
        }

        switch (order.getStatus()) {
            case PENDING:
                if (request.getStatus().equals(OrderStatus.DELIVERING)
                        || request.getStatus().equals(OrderStatus.DELIVERED)) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                break;
            case CONFIRMED:
                if (request.getStatus().equals(OrderStatus.PENDING)
                        || request.getStatus().equals(OrderStatus.DELIVERED)) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                break;
            case DELIVERING:
                if (request.getStatus().equals(OrderStatus.PENDING)
                        || request.getStatus().equals(OrderStatus.CONFIRMED)) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                break;
            case DELIVERED:
                if (!request.getStatus().equals(OrderStatus.DELIVERED)) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                break;
            case CANCELLED:
                if (!request.getStatus().equals(OrderStatus.CANCELLED)) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                break;
            default:
                break;
        }

        // Update warehouse staff can only update order status to CONFIRMED or CANCELLED
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (request.getStatus().equals(OrderStatus.CONFIRMED)
                    && !order.getStatus().equals(OrderStatus.CONFIRMED)) {
                if (orderDetail.getVariant().getWarehouse().getAvailableQuantity()
                        < orderDetail.getQuantity()) {
                    throw new AppException(ErrorCode.ORDER_NOT_UPDATE);
                }
                order.setConfirmedDate(LocalDateTime.now());
                Warehouse warehouse = orderDetail.getVariant().getWarehouse();
                warehouse.setAvailableQuantity(
                        warehouse.getAvailableQuantity() - orderDetail.getQuantity());
                warehouseService.saveWarehouse(warehouse);
            } else if (request.getStatus().equals(OrderStatus.CANCELLED)
                    && !order.getStatus().equals(OrderStatus.CANCELLED)) {

                order.setCanceledDate(LocalDateTime.now());
                order.setCanceledReason(request.getCanceledReason());

                Warehouse warehouse = orderDetail.getVariant().getWarehouse();
                warehouse.setAvailableQuantity(
                        warehouse.getAvailableQuantity() + orderDetail.getQuantity());
                warehouseService.saveWarehouse(warehouse);
            } else if (request.getStatus().equals(OrderStatus.DELIVERED)) {
                Sale sale =
                        Sale.builder()
                                .price(orderDetail.getPrice())
                                .quantity(orderDetail.getQuantity())
                                .product(orderDetail.getVariant().getProduct())
                                .total(orderDetail.getPrice() * orderDetail.getQuantity())
                                .build();
                order.setDeliveredDate(LocalDateTime.now());
                saleService.saveSale(sale);
            } else if (request.getStatus().equals(OrderStatus.DELIVERING)) {
                order.setDeliveringDate(LocalDateTime.now());
            } else if (request.getStatus().equals(OrderStatus.PENDING)) {
                order.setPendingDate(LocalDateTime.now());
            }
        }

        // Logistic staff can only update order status to CONFIRMED or DELIVERED or CANCELLED
        orderMapper.updateOrder(order, request);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getAllOrders(
            PageRequest pageRequest, Specification<Order> specification) {
        User user = authUtil.getUser();
        if (user.getRoles().getFirst().getName().equals(RoleName.ADMIN.name())) {
            return orderRepository
                    .findAll(specification, pageRequest)
                    .map(orderMapper::toOrderResponse);
        }
        Specification<Order> userSpec =
                (root, query, cb) -> cb.equal(root.get("user").get("id"), user.getId());
        Specification<Order> combinedSpec = userSpec.and(specification);

        return orderRepository.findAll(combinedSpec, pageRequest).map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrderById(Integer orderId) {
        User user = authUtil.getUser();
        if (user.getRoles().getFirst().getName().equals(RoleName.ADMIN.name())) {
            Order order =
                    orderRepository
                            .findById(orderId)
                            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            return orderMapper.toOrderResponse(order);
        }
        Order order =
                orderRepository
                        .findByIdAndUserId(orderId, user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }
}
