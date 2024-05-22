package com.tranvansi.ecommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseDetailRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.purchases.CreatePurchaseOrderDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.purchases.PurchaseOrderResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.purchases.IPurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/purchases")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final IPurchaseService purchaseService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchaseOrder(
            @RequestBody CreatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse = purchaseService.createPurchaseOrder(request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<CreatePurchaseOrderDetailResponse>> createPurchaseDetail(
            @RequestBody CreatePurchaseDetailRequest request) {
        CreatePurchaseOrderDetailResponse purchaseOrderDetailResponse =
                purchaseService.createPurchaseDetail(request);
        ApiResponse<CreatePurchaseOrderDetailResponse> response =
                ApiResponse.<CreatePurchaseOrderDetailResponse>builder()
                        .result(purchaseOrderDetailResponse)
                        .message(Message.PURCHASE_ORDER_DETAIL_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/orders/{purchaseOrderId}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updatePurchaseOrder(
            @PathVariable Integer purchaseOrderId,
            @RequestBody UpdatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse =
                purchaseService.updatePurchaseOrder(purchaseOrderId, request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_UPDATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
