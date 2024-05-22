package com.tranvansi.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByVariantId(Integer variantId);

    Optional<Inventory> findBySku(String sku);

    Optional<Inventory> findByVariantIdAndSku(Integer variantId, String sku);
}
