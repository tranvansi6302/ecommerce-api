package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

@Repository
public interface PricePlanRepository
        extends JpaRepository<PricePlan, Integer>, JpaSpecificationExecutor<PricePlan> {
    List<PricePlan> findByVariantIdOrderByStartDateDesc(Integer variantId);

    List<PricePlan> findByVariant(Variant variant);

    @Query(
            "SELECT pp FROM PricePlan pp WHERE pp.variant.id = :variantId AND pp.endDate IS NULL ORDER BY pp.startDate DESC")
    List<PricePlan> findByVariantIdAndEndDateIsNullOrderByStartDateDesc(Integer variantId);
}
