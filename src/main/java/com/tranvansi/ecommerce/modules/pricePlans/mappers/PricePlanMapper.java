package com.tranvansi.ecommerce.modules.pricePlans.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanResponse;

@Mapper(componentModel = "spring")
public interface PricePlanMapper {
    PricePlanResponse toPricePlanResponse(PricePlan pricePlan);

    PricePlanDetailResponse toPricePlanDetailResponse(PricePlan pricePlan);
}
