package com.tranvansi.ecommerce.services.colors;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.requests.colors.UpdateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IColorService {
    @PreAuthorize("hasRole('ADMIN')")
    ColorResponse createColor(CreateColorRequest request);


    Page<ColorResponse> getAllColors(PageRequest pageRequest);

    ColorResponse getColorById(String id);

    @PreAuthorize("hasRole('ADMIN')")
    ColorResponse updateColor(String id, UpdateColorRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteColor(String id);
}
