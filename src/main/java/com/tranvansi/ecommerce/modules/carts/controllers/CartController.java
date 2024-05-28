package com.tranvansi.ecommerce.modules.carts.controllers;

import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.brands.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;
import com.tranvansi.ecommerce.modules.carts.services.ICartService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<CartDetailResponse>>> getAllBrands(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<CartDetailResponse> cartDetailResponses = cartService.getAllProductFromCarts(pageRequest);
        PagedResponse<List<CartDetailResponse>> response =
                BuildResponse.buildPagedResponse(cartDetailResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CartResponse>> addProductToCart(
            @RequestBody @Valid AddToCartRequest request) {
        CartResponse addToCartResponse = cartService.addProductToCart(request);
        ApiResponse<CartResponse> response =
                ApiResponse.<CartResponse>builder()
                        .result(addToCartResponse)
                        .message(Message.ADD_TO_CART_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{cartDetailId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateProductFromCart(
            @PathVariable Integer cartDetailId, @RequestBody @Valid UpdateCartRequest request) {
        CartResponse updateCartResponse = cartService.updateProductFromCart(cartDetailId, request);
        ApiResponse<CartResponse> response =
                ApiResponse.<CartResponse>builder()
                        .result(updateCartResponse)
                        .message(Message.UPDATE_CART_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductFromCart(@PathVariable Integer cartDetailId) {
        cartService.deleteProductFromCart(cartDetailId);
        ApiResponse<Void> response = ApiResponse.<Void>builder().message(Message.DELETE_PRODUCT_CART_DETAIL_SUCCESS.getMessage()).build();
        return ResponseEntity.ok(response);
    }
}
