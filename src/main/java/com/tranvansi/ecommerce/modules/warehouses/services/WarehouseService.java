package com.tranvansi.ecommerce.modules.warehouses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.warehouses.entities.Warehouse;
import com.tranvansi.ecommerce.modules.warehouses.mappers.WarehouseMapper;
import com.tranvansi.ecommerce.modules.warehouses.repositories.WarehouseRepository;
import com.tranvansi.ecommerce.modules.warehouses.responses.WarehouseResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public Page<WarehouseResponse> getAllWarehouses(
            PageRequest pageRequest, Specification<Warehouse> specification) {
        return warehouseRepository
                .findAll(specification, pageRequest)
                .map(warehouseMapper::toWarehouseResponse);
    }
}
