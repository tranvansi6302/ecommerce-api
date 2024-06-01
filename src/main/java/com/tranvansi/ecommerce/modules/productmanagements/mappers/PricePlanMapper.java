package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;

@Mapper(componentModel = "spring")
public interface PricePlanMapper {
    PricePlanResponse toPricePlanResponse(PricePlan pricePlan);

    PricePlanDetailResponse toPricePlanDetailResponse(PricePlan pricePlan);

    void updatePricePlan(@MappingTarget PricePlan pricePlan, UpdatePricePlanRequest request);
}
