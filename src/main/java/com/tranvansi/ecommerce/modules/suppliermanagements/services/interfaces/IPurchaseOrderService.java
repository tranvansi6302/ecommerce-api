package com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    @PreAuthorize("hasRole('ADMIN')")
    Page<PurchaseOrderResponse> getAllPurchaseOrders(
            PageRequest pageRequest, Specification<PurchaseOrder> specification);

    @PreAuthorize("hasRole('ADMIN')")
    PurchaseOrderResponse getPurchaseOrderById(Integer purchaseOrderId);
}
