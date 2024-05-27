package com.tranvansi.ecommerce.modules.purchases.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;

public interface IPurchaseOrderService {
    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse updatePurchaseOrder(
            Integer purchaseOrderId, UpdatePurchaseOrderRequest request);
}
