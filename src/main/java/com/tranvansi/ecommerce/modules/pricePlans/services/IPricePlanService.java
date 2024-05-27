package com.tranvansi.ecommerce.modules.pricePlans.services;

import java.util.List;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface IPricePlanService {
    List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request);

    Page<PricePlanDetailResponse> getHistoryPricePlans(PageRequest pageRequest, Specification<PricePlan> specification);
}
