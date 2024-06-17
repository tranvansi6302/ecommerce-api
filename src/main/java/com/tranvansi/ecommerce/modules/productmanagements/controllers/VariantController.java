package com.tranvansi.ecommerce.modules.productmanagements.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.VariantFilter;
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
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug) {
        VariantFilter filter =
                VariantFilter.builder()
                        .search(search)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
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
}
