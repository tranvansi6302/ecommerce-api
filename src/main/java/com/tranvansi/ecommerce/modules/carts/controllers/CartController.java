package com.tranvansi.ecommerce.modules.carts.controllers;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.AddToCartResponse;
import com.tranvansi.ecommerce.modules.carts.services.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<AddToCartResponse>> addToCart(
            @RequestBody @Valid AddToCartRequest request) {
        AddToCartResponse addToCartResponse = cartService.addToCart(request);
        ApiResponse<AddToCartResponse> response = ApiResponse.<AddToCartResponse>builder()
                .result(addToCartResponse)
                .message(Message.ADD_TO_CART_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
