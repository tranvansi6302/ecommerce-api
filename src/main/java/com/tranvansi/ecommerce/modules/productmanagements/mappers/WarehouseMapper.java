package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.WarehouseResponse;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseResponse toWarehouseResponse(Warehouse warehouse);
}
