package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse.VariantDetail;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

@Mapper(
        componentModel = "spring",
        uses = {PricePlanMapper.class})
public abstract class VariantMapper {

    @Autowired protected PricePlanMapper pricePlanMapper;

    @Mapping(target = "currentPricePlan", expression = "java(getCurrentPricePlan(variant))")
    @Mapping(target = "productId", source = "product.id")
    public abstract VariantDetail toVariantDetail(Variant variant);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productImages", source = "product.productImages")
    public abstract VariantResponse toVariantResponse(Variant variant);

    protected PricePlanResponse getCurrentPricePlan(Variant variant) {
        LocalDateTime now = LocalDateTime.now();
        List<PricePlan> pricePlans = variant.getPricePlans();

        for (PricePlan pricePlan : pricePlans) {
            if (pricePlan.getStartDate().isBefore(now)
                    && (pricePlan.getEndDate() == null || pricePlan.getEndDate().isAfter(now))) {
                return pricePlanMapper.toPricePlanResponse(pricePlan);
            }
        }
        return null;
    }
}
