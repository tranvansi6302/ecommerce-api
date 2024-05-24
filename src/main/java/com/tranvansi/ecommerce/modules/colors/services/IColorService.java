package com.tranvansi.ecommerce.modules.colors.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.colors.requests.CreateColorRequest;
import com.tranvansi.ecommerce.modules.colors.requests.UpdateColorRequest;
import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;

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
