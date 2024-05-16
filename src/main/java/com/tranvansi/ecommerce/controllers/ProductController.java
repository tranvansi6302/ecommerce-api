package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.products.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid  CreateProductRequest request
            ){
        ProductResponse productResponse = productService.createProduct(request);
        ApiResponse<ProductResponse> response = ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .message(Message.CREATE_PRODUCT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
