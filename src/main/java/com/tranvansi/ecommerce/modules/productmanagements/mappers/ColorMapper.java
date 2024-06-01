package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Color;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateColorRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateColorRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ColorResponse;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color createColor(CreateColorRequest request);

    ColorResponse toColorResponse(Color color);

    void updateColor(@MappingTarget Color color, UpdateColorRequest request);
}
