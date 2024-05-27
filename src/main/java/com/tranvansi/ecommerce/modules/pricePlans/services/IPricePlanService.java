package com.tranvansi.ecommerce.modules.pricePlans.services;

import java.util.List;

import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;

public interface IPricePlanService {
    List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request);
}
