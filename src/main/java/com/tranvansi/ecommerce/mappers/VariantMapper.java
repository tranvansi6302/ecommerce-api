package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.variants.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.requests.variants.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.variants.VariantDetailResponse;
import com.tranvansi.ecommerce.entities.Variant;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "size", ignore = true)
    Variant createVariant(CreateVariantRequest request);

    @Mapping(target = "color", source = "color.name")
    VariantDetailResponse toVariantDetailResponse(Variant variant);

    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    void updateVariant(@MappingTarget Variant variant, UpdateVariantRequest request);
}
