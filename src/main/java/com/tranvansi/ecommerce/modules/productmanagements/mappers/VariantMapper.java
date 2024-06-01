package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse.VariantDetail;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

@Mapper(
        componentModel = "spring",
        uses = {ColorMapper.class, SizeMapper.class, PricePlanMapper.class})
public interface VariantMapper {
    VariantDetail toVariantDetail(Variant variant);

    @Mapping(target = "productId", source = "product.id")
    VariantResponse toVariantResponse(Variant variant);
}
