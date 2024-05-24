package com.tranvansi.ecommerce.modules.sizes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.sizes.entities.Size;
import com.tranvansi.ecommerce.modules.sizes.requests.CreateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.requests.UpdateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.responses.SizeResponse;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size createSize(CreateSizeRequest request);

    SizeResponse toSizeResponse(Size size);

    void updateSize(@MappingTarget Size size, UpdateSizeRequest request);
}
