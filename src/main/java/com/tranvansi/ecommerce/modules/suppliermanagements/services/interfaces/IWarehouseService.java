package com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.WarehouseResponse;

public interface IWarehouseService {
    @PreAuthorize("hasRole('ADMIN')")
    Page<WarehouseResponse> getAllWarehouses(
            PageRequest pageRequest, Specification<Warehouse> specification);

    WarehouseResponse getWarehouseById(Integer id);

    boolean existsByVariant(Variant variant);

    void saveWarehouse(Warehouse warehouse);
}
