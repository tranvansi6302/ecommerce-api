package com.tranvansi.ecommerce.modules.carts.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;
import com.tranvansi.ecommerce.modules.carts.services.ICartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @RequestBody @Valid AddToCartRequest request) {
        CartResponse addToCartResponse = cartService.addToCart(request);
        ApiResponse<CartResponse> response =
                ApiResponse.<CartResponse>builder()
                        .result(addToCartResponse)
                        .message(Message.ADD_TO_CART_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{cartDetailId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCart(
            @PathVariable Integer cartDetailId, @RequestBody @Valid UpdateCartRequest request) {
        CartResponse updateCartResponse = cartService.updateCart(cartDetailId, request);
        ApiResponse<CartResponse> response =
                ApiResponse.<CartResponse>builder()
                        .result(updateCartResponse)
                        .message(Message.UPDATE_CART_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
