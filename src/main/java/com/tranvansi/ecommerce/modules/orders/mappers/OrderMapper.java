package com.tranvansi.ecommerce.modules.orders.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.orders.entities.Order;
import com.tranvansi.ecommerce.modules.orders.entities.OrderDetail;
import com.tranvansi.ecommerce.modules.orders.responses.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);

    OrderResponse.OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
