package com.tranvansi.ecommerce.modules.productmanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.PricePlanFilter;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IPricePlanService;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.PricePlanSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/price-plans")
@RequiredArgsConstructor
public class PricePlanController {
    private final IPricePlanService pricePlanService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<PricePlanDetailResponse>>> getAllCurrentPricePlans(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {

        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PricePlanFilter filter =
                PricePlanFilter.builder()
                        .search(search)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .build();

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PricePlanDetailResponse> allCurrentPricePlans =
                pricePlanService.getAllCurrentPricePlans(
                        pageRequest, new PricePlanSpecification(filter));

        PagedResponse<List<PricePlanDetailResponse>> response =
                BuildResponse.buildPagedResponse(allCurrentPricePlans, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<PagedResponse<List<PricePlanDetailResponse>>> getHistoryPricePlans(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PricePlanFilter filter =
                PricePlanFilter.builder()
                        .search(search)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .build();

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PricePlanDetailResponse> historyPricePlans =
                pricePlanService.getHistoryPricePlans(
                        pageRequest, new PricePlanSpecification(filter));

        PagedResponse<List<PricePlanDetailResponse>> response =
                BuildResponse.buildPagedResponse(historyPricePlans, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<PricePlanDetailResponse>>> createPricePlan(
            @RequestBody @Valid CreatePricePlanRequest request) {
        List<PricePlanDetailResponse> createdPricePlans = pricePlanService.createPricePlan(request);
        ApiResponse<List<PricePlanDetailResponse>> response =
                ApiResponse.<List<PricePlanDetailResponse>>builder()
                        .result(createdPricePlans)
                        .message(Message.CREATE_PRICE_PLAN_SUCCESSFUL.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PricePlanDetailResponse>> updatePricePlan(
            @PathVariable("id") Integer id, @RequestBody @Valid UpdatePricePlanRequest request) {
        PricePlanDetailResponse updatedPricePlan = pricePlanService.updatePricePlan(id, request);
        ApiResponse<PricePlanDetailResponse> response =
                ApiResponse.<PricePlanDetailResponse>builder()
                        .result(updatedPricePlan)
                        .message(Message.UPDATE_PRICE_PLAN_SUCCESSFUL.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
