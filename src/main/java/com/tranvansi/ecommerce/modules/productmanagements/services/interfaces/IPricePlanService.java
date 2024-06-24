package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanDetailResponse;

public interface IPricePlanService {
    @PreAuthorize("hasRole('ADMIN')")
    List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<PricePlanDetailResponse> getHistoryPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification);

    @PreAuthorize("hasRole('ADMIN')")
    Page<PricePlanDetailResponse> getAllCurrentPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification);

    @PreAuthorize("hasRole('ADMIN')")
    PricePlanDetailResponse updatePricePlan(Integer pricePlanId, UpdatePricePlanRequest request);

    List<PricePlan> findByVariantIdOrderByStartDateDesc(Integer variantId);
}
