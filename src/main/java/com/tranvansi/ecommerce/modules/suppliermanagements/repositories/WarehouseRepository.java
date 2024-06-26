package com.tranvansi.ecommerce.modules.suppliermanagements.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;

@Repository
public interface WarehouseRepository
        extends JpaRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {
    Optional<Warehouse> findByVariantAndSku(Variant variant, String sku);

    boolean existsByVariant(Variant variant);

    Optional<Warehouse> findByVariant(Variant variant);
}
