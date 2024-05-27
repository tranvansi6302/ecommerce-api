package com.tranvansi.ecommerce.modules.brands.controllers;

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
import com.tranvansi.ecommerce.modules.brands.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.brands.services.IBrandService;

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
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<BrandResponse> brandResponses = brandService.getAllBrands(pageRequest);
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
}
