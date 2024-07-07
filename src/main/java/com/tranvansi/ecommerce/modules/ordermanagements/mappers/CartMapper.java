package com.tranvansi.ecommerce.modules.ordermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.CartDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartResponse;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartDetail addProductToCart(AddToCartRequest request);

    CartResponse addToCartResponse(Cart cart);

    @Mapping(target = "variant.productImages", source = "variant.product.productImages")
    @Mapping(target = "variant.productId", source = "variant.product.id")
    @Mapping(target = "variant.currentPricePlan", ignore = true)
    CartDetailResponse toCartDetailResponse(CartDetail cartDetail);

    void updateProductFromCart(@MappingTarget CartDetail cartDetail, UpdateCartRequest request);
}
