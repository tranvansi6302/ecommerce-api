package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.requests.colors.UpdateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.entities.Color;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color toColor(CreateColorRequest request);

    ColorResponse toColorResponse(Color color);

    void updateColor(@MappingTarget Color color, UpdateColorRequest request);
}
