package com.tranvansi.ecommerce.modules.purchases.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.purchases.services.IPurchaseOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final IPurchaseOrderService purchaseOrderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchaseOrder(
            @RequestBody @Valid CreatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse =
                purchaseOrderService.createPurchaseOrder(request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updatePurchaseOrder(
            @PathVariable Integer id, @RequestBody @Valid UpdatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse =
                purchaseOrderService.updatePurchaseOrder(id, request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_UPDATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
