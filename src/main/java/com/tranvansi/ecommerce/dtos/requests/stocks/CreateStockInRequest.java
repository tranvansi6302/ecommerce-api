package com.tranvansi.ecommerce.dtos.requests.stocks;

import com.tranvansi.ecommerce.entities.PurchaseOrder;
import com.tranvansi.ecommerce.entities.Variant;

public class CreateStockInRequest {
    private Integer quantity;

    private Double pricing;

    private String sku;

    private Variant variant;

    private PurchaseOrder purchaseOrder;
}
