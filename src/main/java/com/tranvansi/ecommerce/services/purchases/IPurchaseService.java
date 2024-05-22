package com.tranvansi.ecommerce.services.purchases;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseDetailRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.responses.purchases.CreatePurchaseOrderDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.purchases.PurchaseOrderResponse;

public interface IPurchaseService {

    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request);

    CreatePurchaseOrderDetailResponse createPurchaseDetail(CreatePurchaseDetailRequest request);

    PurchaseOrderResponse updatePurchaseOrder(
            Integer purchaseOrderId, UpdatePurchaseOrderRequest request);
}
