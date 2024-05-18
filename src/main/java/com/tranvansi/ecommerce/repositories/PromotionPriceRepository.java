package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.PromotionPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionPriceRepository extends JpaRepository<PromotionPrice, Integer> {
}