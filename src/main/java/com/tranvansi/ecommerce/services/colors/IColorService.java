package com.tranvansi.ecommerce.services.colors;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IColorService {
    ColorResponse createColor(CreateColorRequest request);

    Page<ColorResponse> getAllColors(PageRequest pageRequest);
}
