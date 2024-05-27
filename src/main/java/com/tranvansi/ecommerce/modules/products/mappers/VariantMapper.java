package com.tranvansi.ecommerce.modules.products.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.colors.mappers.ColorMapper;
import com.tranvansi.ecommerce.modules.pricePlans.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse.VariantDetail;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.sizes.mappers.SizeMapper;

@Mapper(
        componentModel = "spring",
        uses = {ColorMapper.class, SizeMapper.class, PricePlanMapper.class})
public interface VariantMapper {
    VariantDetail toVariantDetail(Variant variant);

    VariantResponse toVariantResponse(Variant variant);
}
