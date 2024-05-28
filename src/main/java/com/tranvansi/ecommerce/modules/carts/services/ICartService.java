package com.tranvansi.ecommerce.modules.carts.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;

public interface ICartService {
    @PreAuthorize("hasRole('USER')")
    CartResponse addToCart(AddToCartRequest request);

    @PreAuthorize("hasRole('USER')")
    CartResponse updateCart(Integer cartDetailId, UpdateCartRequest request);
}
