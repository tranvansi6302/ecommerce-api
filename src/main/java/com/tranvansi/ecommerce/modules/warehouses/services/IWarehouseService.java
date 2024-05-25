package com.tranvansi.ecommerce.modules.warehouses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.warehouses.entities.Warehouse;
import com.tranvansi.ecommerce.modules.warehouses.responses.WarehouseResponse;

public interface IWarehouseService {
    public Page<WarehouseResponse> getAllWarehouses(
            PageRequest pageRequest, Specification<Warehouse> specification);
}
