package com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.PurchaseOrderResponse;

public interface IPurchaseOrderService {
    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse updatePurchaseOrder(
            Integer purchaseOrderId, UpdatePurchaseOrderRequest request);
}
