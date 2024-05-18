package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
    boolean existsBySku(String sku);
}