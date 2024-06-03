package com.tranvansi.ecommerce.modules.productmanagements.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tranvansi.ecommerce.components.constants.FileConstant;
import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.components.utils.FileUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.filters.ProductFilter;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UploadProductImagesRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IProductService;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.ProductSpecification;

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
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "price_min", required = false) Double priceMin,
            @RequestParam(name = "price_max", required = false) Double priceMax,
            @RequestParam(name = "rating", required = false) Integer ratingMin) {

        Sort sort;
        if (!"price".equalsIgnoreCase(sortBy) && !"sold".equalsIgnoreCase(sortBy)) {
            sort =
                    "asc".equalsIgnoreCase(sortOrder)
                            ? Sort.by("createdAt").ascending()
                            : Sort.by("createdAt").descending();
        } else {
            sort = Sort.unsorted();
        }

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

        // Sort by price or sold in-memory if requested
        if ("price".equalsIgnoreCase(sortBy) || "sold".equalsIgnoreCase(sortBy)) {
            Comparator<ProductDetailResponse> comparator = null;
            if ("price".equalsIgnoreCase(sortBy)) {
                comparator =
                        Comparator.comparing(
                                product ->
                                        product.getVariants().stream()
                                                .map(
                                                        ProductDetailResponse.VariantDetail
                                                                ::getCurrentPricePlan)
                                                .map(PricePlanResponse::getSalePrice)
                                                .max(Double::compare)
                                                .orElse(0.0)); // Use 0.0 as default if no salePrice
                // is
            } else if ("sold".equalsIgnoreCase(sortBy)) {
                comparator = Comparator.comparing(ProductDetailResponse::getSold);
            }

            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = Objects.requireNonNull(comparator).reversed();
            }

            List<ProductDetailResponse> sortedProducts =
                    productDetailResponses.getContent().stream()
                            .sorted(Objects.requireNonNull(comparator))
                            .collect(Collectors.toList());

            productDetailResponses =
                    new PageImpl<>(
                            sortedProducts, pageRequest, productDetailResponses.getTotalElements());
        }

        // Filter rating to ProductDetailResponse
        if (ratingMin != null) {
            List<ProductDetailResponse> filteredProducts =
                    productDetailResponses.getContent().stream()
                            .filter(
                                    product ->
                                            product.getAverageRating() != null
                                                    && product.getAverageRating() >= ratingMin)
                            .collect(Collectors.toList());

            productDetailResponses =
                    new PageImpl<>(filteredProducts, pageRequest, filteredProducts.size());
        }

        PagedResponse<List<ProductDetailResponse>> response =
                BuildResponse.buildPagedResponse(productDetailResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductById(
            @PathVariable Integer id) {
        ProductDetailResponse productDetailResponse = productService.getProductById(id);
        ApiResponse<ProductDetailResponse> response =
                ApiResponse.<ProductDetailResponse>builder().result(productDetailResponse).build();
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

    @PatchMapping("/{id}/upload-images")
    public ResponseEntity<ApiResponse<ProductResponse>> uploadImageProducts(
            @PathVariable Integer id, @RequestPart("files") List<MultipartFile> files) {
        UploadProductImagesRequest request =
                UploadProductImagesRequest.builder().files(files).build();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_USER_AVATAR_REQUIRED);
            }

            if (!FileUtil.isImageFile(file)) {
                throw new AppException(ErrorCode.INVALID_USER_AVATAR_FORMAT);
            }
            if (file.getSize() > FileConstant.MAX_FILE_SIZE_MB) { // 5MB
                throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
            }
        }

        ProductResponse productResponse = productService.uploadImageProducts(id, request);
        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .result(productResponse)
                        .message(Message.UPLOAD_IMAGE_PRODUCT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
