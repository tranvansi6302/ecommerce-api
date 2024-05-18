package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.OriginalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalPriceRepository extends JpaRepository<OriginalPrice, Integer> {
}