package com.tranvansi.ecommerce.modules.pricePlans.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;

public interface IPricePlanService {
    List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request);

    Page<PricePlanDetailResponse> getHistoryPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification);

    Page<PricePlanDetailResponse> getAllCurrentPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification);
}
