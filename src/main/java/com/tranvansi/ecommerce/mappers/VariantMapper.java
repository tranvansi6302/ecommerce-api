package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.prices.OriginalPriceRequest;
import com.tranvansi.ecommerce.dtos.requests.prices.PromotionPriceRequest;
import com.tranvansi.ecommerce.dtos.requests.variants.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.variants.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.prices.OriginalPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.prices.PromotionPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.variants.VariantDetailResponse;
import com.tranvansi.ecommerce.entities.OriginalPrice;
import com.tranvansi.ecommerce.entities.PromotionPrice;
import com.tranvansi.ecommerce.entities.Variant;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "size", ignore = true)
    Variant createVariant(CreateVariantRequest request);

    OriginalPrice createOriginalPrice(OriginalPriceRequest request);

    PromotionPrice createPromotionPrice(PromotionPriceRequest request);

    @Mapping(target = "color", source = "color.name")
    VariantDetailResponse toVariantDetailResponse(Variant variant);

    OriginalPriceResponse toOriginalPriceResponse(OriginalPrice originalPrice);

    PromotionPriceResponse toPromotionPriceResponse(PromotionPrice promotionPrice);

    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    void updateVariant(@MappingTarget Variant variant, UpdateVariantRequest request);

    @Mapping(target = "variant", ignore = true)
    void updatePromotionPrice(
            @MappingTarget PromotionPrice promotionPrice, PromotionPriceRequest request);
}
