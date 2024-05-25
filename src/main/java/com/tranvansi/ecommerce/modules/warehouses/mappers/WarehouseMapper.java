package com.tranvansi.ecommerce.modules.warehouses.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.warehouses.entities.Warehouse;
import com.tranvansi.ecommerce.modules.warehouses.responses.WarehouseResponse;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseResponse toWarehouseResponse(Warehouse warehouse);
}
