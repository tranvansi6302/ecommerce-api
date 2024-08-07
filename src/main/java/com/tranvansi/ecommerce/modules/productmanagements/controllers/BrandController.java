package com.tranvansi.ecommerce.modules.productmanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.BrandStatus;
import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.BrandFilter;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateManyStatusBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IBrandService;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.BrandSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/brands")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandService brandService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<BrandResponse>>> getAllBrands(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "status", required = false) BrandStatus status,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        BrandFilter filter = BrandFilter.builder().search(search).status(status).build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<BrandResponse> brandResponses =
                brandService.getAllBrands(pageRequest, new BrandSpecification(filter));
        PagedResponse<List<BrandResponse>> response =
                BuildResponse.buildPagedResponse(brandResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Integer id) {
        BrandResponse brandResponse = brandService.getBrandById(id);
        ApiResponse<BrandResponse> response =
                ApiResponse.<BrandResponse>builder().result(brandResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(
            @RequestBody @Valid CreateBrandRequest request) {
        BrandResponse brandResponse = brandService.createBrand(request);
        ApiResponse<BrandResponse> response =
                ApiResponse.<BrandResponse>builder()
                        .result(brandResponse)
                        .message(Message.CREATE_BRAND_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable Integer id, @RequestBody @Valid UpdateBrandRequest request) {
        BrandResponse brandResponse = brandService.updateBrand(id, request);
        ApiResponse<BrandResponse> response =
                ApiResponse.<BrandResponse>builder()
                        .message(Message.UPDATE_BRAND_SUCCESS.getMessage())
                        .result(brandResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
        ApiResponse<BrandResponse> response =
                ApiResponse.<BrandResponse>builder()
                        .message(Message.DELETE_BRAND_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<BrandResponse>> updateManyStatusBrand(
            @RequestBody @Valid UpdateManyStatusBrandRequest request) {
        brandService.updateManyStatusBrand(request);
        ApiResponse<BrandResponse> response =
                ApiResponse.<BrandResponse>builder()
                        .message(Message.UPDATE_MANY_STATUS_BRAND_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
