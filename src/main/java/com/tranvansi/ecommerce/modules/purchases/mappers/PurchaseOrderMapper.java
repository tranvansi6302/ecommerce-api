package com.tranvansi.ecommerce.modules.purchases.mappers;


import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "purchaseDetails", ignore = true)
    PurchaseOrder createPurchaseOrder(CreatePurchaseOrderRequest request);

    @Mapping(target = "purchaseOrderDate", source = "purchaseOrderDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder);
}
