package com.tranvansi.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.products.UpdateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.products.IProductService;
import com.tranvansi.ecommerce.services.products.IVariantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final IProductService productService;
    private final IVariantService variantService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid CreateProductRequest request) {

        ProductResponse productResponse = productService.createProduct(request);
        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .result(productResponse)
                        .message(Message.CREATE_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Integer id, @RequestBody @Valid UpdateProductRequest request) {
        ProductResponse productResponse = productService.updateProduct(id, request);
        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .result(productResponse)
                        .message(Message.UPDATE_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<?>> deleteSoftProduct(@PathVariable Integer id) {
        productService.deleteSoftProduct(id);
        ApiResponse<?> response =
                ApiResponse.builder()
                        .message(Message.DELETE_SOFT_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<?>> restoreProduct(@PathVariable Integer id) {
        productService.restoreProduct(id);
        ApiResponse<?> response =
                ApiResponse.builder().message(Message.RESTORE_PRODUCT_SUCCESS.getMessage()).build();
        return ResponseEntity.ok(response);
    }
}
