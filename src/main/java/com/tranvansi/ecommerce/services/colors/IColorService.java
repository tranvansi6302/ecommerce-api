package com.tranvansi.ecommerce.services.colors;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;

public interface IColorService {
    ColorResponse createColor(CreateColorRequest request);
}
