package com.tranvansi.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.OriginalPrice;

@Repository
public interface OriginalPriceRepository extends JpaRepository<OriginalPrice, Integer> {
    Optional<OriginalPrice> findByVariantId(Integer variantId);
}
