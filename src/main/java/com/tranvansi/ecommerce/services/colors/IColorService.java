package com.tranvansi.ecommerce.services.colors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.requests.colors.UpdateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;

public interface IColorService {
    @PreAuthorize("hasRole('ADMIN')")
    ColorResponse createColor(CreateColorRequest request);

    Page<ColorResponse> getAllColors(PageRequest pageRequest);

    ColorResponse getColorById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    ColorResponse updateColor(Integer id, UpdateColorRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteColor(Integer id);
}
