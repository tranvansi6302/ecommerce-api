package com.tranvansi.ecommerce.modules.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.products.entities.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
    boolean existsBySku(String sku);
}
