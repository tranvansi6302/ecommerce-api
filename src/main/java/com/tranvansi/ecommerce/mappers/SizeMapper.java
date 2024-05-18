package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.requests.sizes.UpdateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import com.tranvansi.ecommerce.entities.Size;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size toSize(CreateSizeRequest request);

    SizeResponse toSizeResponse(Size size);

    void updateSize(@MappingTarget Size size, UpdateSizeRequest request);
}
