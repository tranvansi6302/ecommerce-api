package com.tranvansi.ecommerce.modules.carts.services;

import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.AddToCartResponse;

public interface ICartService {
    AddToCartResponse addToCart(AddToCartRequest request);
}
