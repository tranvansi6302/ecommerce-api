package com.tranvansi.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranvansi.ecommerce.dtos.requests.price_lists.CreatePriceListRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.price_lists.CreatePriceListResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.price_lists.IPriceListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/price-lists")
@RequiredArgsConstructor
public class PriceListController {
    private final IPriceListService priceListService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreatePriceListResponse>> createPriceList(
            @RequestBody CreatePriceListRequest request) {
        CreatePriceListResponse createPriceListResponse = priceListService.createPriceList(request);
        ApiResponse<CreatePriceListResponse> apiResponse =
                ApiResponse.<CreatePriceListResponse>builder()
                        .result(createPriceListResponse)
                        .message(Message.CREATE_PRICE_LIST_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
