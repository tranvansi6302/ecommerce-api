package com.tranvansi.ecommerce.modules.pricePlans.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;

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
}
