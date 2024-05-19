package com.tranvansi.ecommerce.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.PromotionPrice;

@Repository
public interface PromotionPriceRepository extends JpaRepository<PromotionPrice, Integer> {

    Optional<PromotionPrice> findByVariantId(Integer variantId);

    List<PromotionPrice> findByEndDateBefore(LocalDateTime endDate);

    boolean existsByVariantIdAndStatus(Integer variantId, Integer status);
}
