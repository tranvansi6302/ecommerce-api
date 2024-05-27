package com.tranvansi.ecommerce.modules.pricePlans.controllers;

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
import com.tranvansi.ecommerce.modules.pricePlans.filters.PricePlanFilter;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.pricePlans.services.IPricePlanService;
import com.tranvansi.ecommerce.modules.pricePlans.specifications.PricePlanSpecification;

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
            @RequestParam(name = "name", required = false) String variantName,
            @RequestParam(name = "sku", required = false) String sku,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {

        Sort sort;
        if ("price".equalsIgnoreCase(sortBy)) {
            sort =
                    "asc".equalsIgnoreCase(sortOrder)
                            ? Sort.by("salePrice").ascending()
                            : Sort.by("salePrice").descending();
        } else {
            sort = Sort.by("createdAt").descending();
        }

        PricePlanFilter filter =
                PricePlanFilter.builder()
                        .variantName(variantName)
                        .sku(sku)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .build();

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PricePlanDetailResponse> pricePlanResponses =
                pricePlanService.getAllCurrentPricePlans(
                        pageRequest, new PricePlanSpecification(filter));

        // Sort by price within the page results
        if ("price".equalsIgnoreCase(sortBy)) {
            Comparator<PricePlanDetailResponse> comparator =
                    Comparator.comparing(PricePlanDetailResponse::getSalePrice);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }
            List<PricePlanDetailResponse> sortedResponses =
                    pricePlanResponses.getContent().stream()
                            .sorted(comparator)
                            .collect(Collectors.toList());
            pricePlanResponses =
                    new PageImpl<>(
                            sortedResponses, pageRequest, pricePlanResponses.getTotalElements());
        }

        PagedResponse<List<PricePlanDetailResponse>> response =
                BuildResponse.buildPagedResponse(pricePlanResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<PagedResponse<List<PricePlanDetailResponse>>> getHistoryPricePlans(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "name", required = false) String variantName,
            @RequestParam(name = "sku", required = false) String sku,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PricePlanFilter filter =
                PricePlanFilter.builder().variantName(variantName).sku(sku).build();

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PricePlanDetailResponse> colorResponses =
                pricePlanService.getHistoryPricePlans(
                        pageRequest, new PricePlanSpecification(filter));

        PagedResponse<List<PricePlanDetailResponse>> response =
                BuildResponse.buildPagedResponse(colorResponses, pageRequest);
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
