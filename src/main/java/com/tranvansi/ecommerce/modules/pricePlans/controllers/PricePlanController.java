package com.tranvansi.ecommerce.modules.pricePlans.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
