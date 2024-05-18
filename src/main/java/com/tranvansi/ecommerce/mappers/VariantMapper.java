package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.products.OriginalPriceRequest;
import com.tranvansi.ecommerce.dtos.requests.products.PromotionPriceRequest;
import com.tranvansi.ecommerce.dtos.responses.products.OriginalPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.products.PromotionPriceResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import com.tranvansi.ecommerce.entities.OriginalPrice;
import com.tranvansi.ecommerce.entities.PromotionPrice;
import com.tranvansi.ecommerce.entities.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "variant", ignore = true)
    OriginalPrice toOriginalPrice(OriginalPriceRequest request);



    @Mapping(target = "color", source = "color.name")

    VariantDetailResponse toVariantDetailResponse(Variant variant);

    OriginalPriceResponse toOriginalPriceResponse(OriginalPrice originalPrice);

    PromotionPrice toPromotionPrice(PromotionPriceRequest request);

    PromotionPriceResponse toPromotionPriceResponse(PromotionPrice promotionPrice);
}