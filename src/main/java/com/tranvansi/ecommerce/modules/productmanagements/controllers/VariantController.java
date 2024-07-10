package com.tranvansi.ecommerce.modules.productmanagements.controllers;

import java.util.List;

import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteManyVariantRequest;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.enums.ProductStatus;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.VariantFilter;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateVariantRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.VariantSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/variants")
@RequiredArgsConstructor
public class VariantController {
    private final IVariantService variantService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<VariantResponse>>> getAllVariants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "status", defaultValue = "ACTIVE") ProductStatus status) {
        VariantFilter filter =
                VariantFilter.builder()
                        .search(search)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .status(status)
                        .build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<VariantResponse> variantResponses =
                variantService.getAllVariants(pageRequest, new VariantSpecification(filter));
        PagedResponse<List<VariantResponse>> response =
                BuildResponse.buildPagedResponse(variantResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<VariantResponse>> updateVariant(
            @PathVariable Integer id, @RequestBody @Valid UpdateVariantRequest request) {
        VariantResponse variantResponse = variantService.updateVariant(id, request);
        ApiResponse<VariantResponse> response =
                ApiResponse.<VariantResponse>builder()
                        .result(variantResponse)
                        .message(Message.UPDATE_VARIANT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<String>> deleteManyVariants(@RequestBody DeleteManyVariantRequest request) {
        variantService.deleteManyVariants(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_MANY_VARIANT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
