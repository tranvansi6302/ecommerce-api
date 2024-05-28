package com.tranvansi.ecommerce.modules.carts.mappers;

import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.AddToCartResponse;
import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartDetail addToCart(AddToCartRequest request);

    AddToCartResponse addToCartResponse(Cart cart);

    CartDetailResponse cartDetailResponse(CartDetail cartDetail);
}
