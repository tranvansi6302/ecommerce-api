package com.tranvansi.ecommerce.modules.carts.services;

import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;

import java.util.List;

public interface ICartService {
    @PreAuthorize("hasRole('USER')")
    CartResponse addProductToCart(AddToCartRequest request);

    @PreAuthorize("hasRole('USER')")
    CartResponse updateProductFromCart(Integer cartDetailId, UpdateCartRequest request);

    @PreAuthorize("hasRole('USER')")
    Page<CartDetailResponse> getAllProductFromCarts(PageRequest pageRequest);

    @PreAuthorize("hasRole('USER')")
    void deleteProductFromCart(Integer cartDetailId);
}
