package com.tranvansi.ecommerce.modules.pricePlans.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;

@Repository
public interface PricePlanRepository extends JpaRepository<PricePlan, Integer> {
    List<PricePlan> findByVariantIdOrderByStartDateDesc(Integer variantId);

    @Query(
            "SELECT pp FROM PricePlan pp WHERE pp.variant.id = :variantId AND pp.endDate IS NULL ORDER BY pp.startDate DESC")
    List<PricePlan> findByVariantIdAndEndDateIsNullOrderByStartDateDesc(Integer variantId);
}
