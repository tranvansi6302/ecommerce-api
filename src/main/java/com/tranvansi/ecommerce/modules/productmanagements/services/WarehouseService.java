package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.WarehouseMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.WarehouseRepository;
import com.tranvansi.ecommerce.modules.productmanagements.responses.WarehouseResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IWarehouseService;

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

    @Override
    public boolean existsByVariant(Variant variant) {
        return !warehouseRepository.existsByVariant(variant);
    }

    @Override
    public void saveWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }
}
