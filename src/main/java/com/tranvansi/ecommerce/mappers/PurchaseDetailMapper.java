package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.dtos.requests.purchases.PurchaseDetailRequest;
import com.tranvansi.ecommerce.entities.PurchaseDetail;

@Mapper(componentModel = "spring")
public interface PurchaseDetailMapper {
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "purchaseOrder", ignore = true)
    @Mapping(target = "sku", ignore = true)
    PurchaseDetail createPurchaseDetail(PurchaseDetailRequest request);
}
