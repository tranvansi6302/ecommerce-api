package com.tranvansi.ecommerce.modules.purchases.controllers;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.purchases.services.IPurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final IPurchaseOrderService purchaseOrderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchaseOrder(
            @RequestBody @Valid CreatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.createPurchaseOrder(request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
