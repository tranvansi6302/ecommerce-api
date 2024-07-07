package com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.DeleteManyProductCart;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartResponse;

public interface ICartService {
    @PreAuthorize("hasRole('USER')")
    CartResponse addProductToCart(AddToCartRequest request);

    @PreAuthorize("hasRole('USER')")
    CartResponse updateProductFromCart(Integer cartDetailId, UpdateCartRequest request);

    @PreAuthorize("hasRole('USER')")
    Page<CartDetailResponse> getAllProductFromCarts(PageRequest pageRequest);

    @PreAuthorize("hasRole('USER')")
    void deleteProductFromCart(DeleteManyProductCart cartDetailId);

    Cart findByUserId(Integer userId);

    void saveCart(Cart cart);
}
