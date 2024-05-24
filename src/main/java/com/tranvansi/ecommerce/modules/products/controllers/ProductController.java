package com.tranvansi.ecommerce.modules.products.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.products.filters.ProductFilter;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductResponse;
import com.tranvansi.ecommerce.modules.products.services.IProductService;
import com.tranvansi.ecommerce.modules.products.specifications.ProductSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction,
            ProductFilter filter) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ProductResponse> productDetailResponses =
                productService.getAllProducts(pageRequest, new ProductSpecification(filter));
        PagedResponse<List<ProductResponse>> response =
                BuildResponse.buildPagedResponse(productDetailResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(
            @RequestBody @Valid CreateProductRequest request) {

        CreateProductResponse productResponse = productService.createProduct(request);
        ApiResponse<CreateProductResponse> response =
                ApiResponse.<CreateProductResponse>builder()
                        .result(productResponse)
                        .message(Message.CREATE_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateProductResponse>> updateProduct(
            @PathVariable Integer id, @RequestBody @Valid UpdateProductRequest request) {
        CreateProductResponse productResponse = productService.updateProduct(id, request);
        ApiResponse<CreateProductResponse> response =
                ApiResponse.<CreateProductResponse>builder()
                        .result(productResponse)
                        .message(Message.UPDATE_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
