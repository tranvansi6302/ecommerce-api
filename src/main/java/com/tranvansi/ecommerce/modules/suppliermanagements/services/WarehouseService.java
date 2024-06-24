package com.tranvansi.ecommerce.modules.suppliermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.suppliermanagements.mappers.WarehouseMapper;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.WarehouseRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.WarehouseResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IWarehouseService;

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
    public WarehouseResponse getWarehouseById(Integer id) {
        return warehouseRepository
                .findById(id)
                .map(warehouseMapper::toWarehouseResponse)
                .orElseThrow(() -> new AppException((ErrorCode.WAREHOUSE_NOT_FOUND)));
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
