package com.tranvansi.ecommerce.modules.suppliermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.PurchaseOrderResponse;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "purchaseDetails", ignore = true)
    PurchaseOrder createPurchaseOrder(CreatePurchaseOrderRequest request);

    @Mapping(
            target = "purchaseOrderDate",
            source = "purchaseOrderDate",
            dateFormat = "dd-MM-yyyy HH:mm:ss")
    PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder);
}
