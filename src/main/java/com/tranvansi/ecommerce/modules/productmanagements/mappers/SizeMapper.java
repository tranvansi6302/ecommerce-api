package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Size;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateSizeRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateSizeRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.SizeResponse;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size createSize(CreateSizeRequest request);

    SizeResponse toSizeResponse(Size size);

    void updateSize(@MappingTarget Size size, UpdateSizeRequest request);
}
