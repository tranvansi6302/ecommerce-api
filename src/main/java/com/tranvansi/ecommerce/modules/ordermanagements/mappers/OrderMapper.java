package com.tranvansi.ecommerce.modules.ordermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.OrderDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateOrderRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "variant.productId", source = "variant.product.id")
    @Mapping(target = "variant.productImages", source = "variant.product.productImages")
    @Mapping(target = "variant.currentPricePlan", ignore = true)
    OrderResponse.OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    void updateOrder(@MappingTarget Order order, UpdateOrderRequest request);
}
