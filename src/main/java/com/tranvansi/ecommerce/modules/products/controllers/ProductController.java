package com.tranvansi.ecommerce.modules.products.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.products.filters.ProductFilter;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse;
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
    public ResponseEntity<PagedResponse<List<ProductDetailResponse>>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "name", required = false) String productName,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "sort_order", required = false) String sortOrder,
            @RequestParam(name = "price_min", required = false) Double priceMin,
            @RequestParam(name = "price_max", required = false) Double priceMax,
            @RequestParam(defaultValue = "desc") String sort_direction) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();

        ProductFilter filter =
                ProductFilter.builder()
                        .productName(productName)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .priceMin(priceMin)
                        .priceMax(priceMax)
                        .build();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ProductDetailResponse> productDetailResponses =
                productService.getAllProducts(pageRequest, new ProductSpecification(filter));

        // Sort by price
        if ("price".equals(sortBy)) {
            Comparator<ProductDetailResponse> comparator =
                    Comparator.comparing(
                            product ->
                                    product.getVariants().stream()
                                            .map(
                                                    ProductDetailResponse.VariantDetail
                                                            ::getCurrentPricePlan)
                                            .map(PricePlanResponse::getSalePrice)
                                            .max(Double::compare)
                                            .orElseThrow(IllegalStateException::new));
            if ("desc".equals(sortOrder)) {
                comparator = comparator.reversed();
            }
            List<ProductDetailResponse> sortedProducts =
                    productDetailResponses.stream().sorted(comparator).collect(Collectors.toList());
            productDetailResponses =
                    new PageImpl<>(
                            sortedProducts, pageRequest, productDetailResponses.getTotalElements());
        }
        PagedResponse<List<ProductDetailResponse>> response =
                BuildResponse.buildPagedResponse(productDetailResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductById(@PathVariable Integer id) {
        ProductDetailResponse productDetailResponse = productService.getProductById(id);
        ApiResponse<ProductDetailResponse> response =
                ApiResponse.<ProductDetailResponse>builder()
                        .result(productDetailResponse)
                        .build();
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
