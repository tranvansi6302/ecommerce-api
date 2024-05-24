package com.tranvansi.ecommerce.modules.colors.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.colors.entities.Color;
import com.tranvansi.ecommerce.modules.colors.requests.CreateColorRequest;
import com.tranvansi.ecommerce.modules.colors.requests.UpdateColorRequest;
import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color createColor(CreateColorRequest request);

    ColorResponse toColorResponse(Color color);

    void updateColor(@MappingTarget Color color, UpdateColorRequest request);
}
