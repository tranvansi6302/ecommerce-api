package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.responses.purchases.PurchaseOrderResponse;
import com.tranvansi.ecommerce.entities.PurchaseOrder;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "status", ignore = true)
    PurchaseOrder createPurchaseOrder(CreatePurchaseOrderRequest request);

    PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder);

    void updatePurchaseOrder(
            @MappingTarget PurchaseOrder purchaseOrder, UpdatePurchaseOrderRequest request);
}
