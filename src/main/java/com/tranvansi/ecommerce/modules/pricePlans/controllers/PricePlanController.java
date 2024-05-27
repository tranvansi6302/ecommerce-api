package com.tranvansi.ecommerce.modules.pricePlans.controllers;

import java.util.List;

import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.pricePlans.filters.PricePlanFilter;
import com.tranvansi.ecommerce.modules.pricePlans.specifications.PricePlanSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.pricePlans.services.IPricePlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/price-plans")
@RequiredArgsConstructor
public class PricePlanController {
    private final IPricePlanService pricePlanService;

    @GetMapping("/history")
    public ResponseEntity<PagedResponse<List<PricePlanDetailResponse>>> getAllColors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "name", required = false) String variantName,
            @RequestParam(name = "sku", required = false) String sku,
            @RequestParam(defaultValue = "desc") String sort_direction) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PricePlanFilter filter = PricePlanFilter
                .builder()
                .variantName(variantName)
                .sku(sku)
                .build();

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PricePlanDetailResponse> colorResponses = pricePlanService.getHistoryPricePlans(pageRequest, new PricePlanSpecification(filter));

        PagedResponse<List<PricePlanDetailResponse>> response =
                BuildResponse.buildPagedResponse(colorResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<PricePlanDetailResponse>>> createPricePlan(
            @RequestBody CreatePricePlanRequest request) {
        List<PricePlanDetailResponse> createdPricePlans = pricePlanService.createPricePlan(request);
        ApiResponse<List<PricePlanDetailResponse>> response =
                ApiResponse.<List<PricePlanDetailResponse>>builder()
                        .result(createdPricePlans)
                        .message(Message.CREATE_PRICE_PLAN_SUCCESSFUL.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
