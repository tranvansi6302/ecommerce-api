package com.tranvansi.ecommerce.modules.productmanagements.services;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductSalesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return warehouseRepository.findById(id)
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

    @Override
    public Page<ProductSalesResponse> getAllProductSales(PageRequest pageRequest) {
        Page<Warehouse> warehousesPage = warehouseRepository.findAll(pageRequest);

        Map<Integer, ProductSalesResponse> productSalesResponseMap = new HashMap<>();

        warehousesPage.forEach(warehouse -> {
            ProductSalesResponse response = warehouseMapper.toProductSalesResponse(warehouse);

            // Filter variants to only include those present in the warehouse
            response.getProduct().setVariants(
                    response.getProduct().getVariants().stream()
                            .filter(variant -> variant.getId().equals(warehouse.getVariant().getId()))
                            .collect(Collectors.toList())
            );

            ProductSalesResponse existingResponse = productSalesResponseMap.get(response.getProduct().getId());

            if (existingResponse == null) {
                productSalesResponseMap.put(response.getProduct().getId(), response);
            } else {
                existingResponse.getProduct().getVariants().addAll(response.getProduct().getVariants());
            }
        });

        List<ProductSalesResponse> productSalesResponses = new ArrayList<>(productSalesResponseMap.values());

        return new PageImpl<>(productSalesResponses, pageRequest, warehousesPage.getTotalElements());
    }
}
