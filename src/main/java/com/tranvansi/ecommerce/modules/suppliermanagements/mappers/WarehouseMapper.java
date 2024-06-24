package com.tranvansi.ecommerce.modules.suppliermanagements.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.ProductMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.WarehouseResponse;

@Mapper(
        componentModel = "spring",
        uses = {ProductMapper.class, PricePlanMapper.class, VariantMapper.class})
public abstract class WarehouseMapper {

    @Autowired private ProductMapper productMapper;

    @Mapping(target = "variant.pricePlans", source = "variant.pricePlans")
    @Mapping(target = "variant.productImages", source = "variant.product.productImages")
    @Mapping(target = "variant.productId", source = "variant.product.id")
    public abstract WarehouseResponse toWarehouseResponse(Warehouse warehouse);
}
