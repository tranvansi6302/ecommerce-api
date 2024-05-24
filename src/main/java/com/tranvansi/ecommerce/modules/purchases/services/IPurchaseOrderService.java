package com.tranvansi.ecommerce.modules.purchases.services;

import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;

public interface IPurchaseOrderService {
    PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request);
}
