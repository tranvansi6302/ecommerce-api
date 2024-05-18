package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.products.OriginalPriceRequest;
import com.tranvansi.ecommerce.dtos.requests.products.PromotionPriceRequest;
import com.tranvansi.ecommerce.dtos.responses.products.OriginalPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.products.PromotionPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantDetailResponse;
import com.tranvansi.ecommerce.entities.OriginalPrice;
import com.tranvansi.ecommerce.entities.PromotionPrice;
import com.tranvansi.ecommerce.entities.Variant;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "size", ignore = true)
    Variant toVariant(CreateVariantRequest request);

    @Mapping(target = "variant", ignore = true)
    OriginalPrice toOriginalPrice(OriginalPriceRequest request);

    @Mapping(target = "variant", ignore = true)
    PromotionPrice toPromotionPrice(PromotionPriceRequest request);

    @Mapping(target = "color", source = "color.name")
    VariantDetailResponse toVariantDetailResponse(Variant variant);


    OriginalPriceResponse toOriginalPriceResponse(OriginalPrice originalPrice);

    PromotionPriceResponse toPromotionPriceResponse(PromotionPrice promotionPrice);

    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "product",ignore = true)
    void updateVariant(@MappingTarget Variant variant, UpdateVariantRequest request);

    @Mapping(target = "variant", ignore = true)
    void updatePromotionPrice(@MappingTarget PromotionPrice promotionPrice, PromotionPriceRequest request);
}
