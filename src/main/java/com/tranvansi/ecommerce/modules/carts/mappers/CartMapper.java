package com.tranvansi.ecommerce.modules.carts.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartDetail addToCart(AddToCartRequest request);

    CartResponse addToCartResponse(Cart cart);

    CartDetailResponse cartDetailResponse(CartDetail cartDetail);

    void updateCart(@MappingTarget CartDetail cartDetail, UpdateCartRequest request);
}
