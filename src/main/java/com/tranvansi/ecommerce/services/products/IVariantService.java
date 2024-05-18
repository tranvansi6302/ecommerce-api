package com.tranvansi.ecommerce.services.products;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IVariantService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    VariantResponse createVariant(Integer productId, CreateVariantRequest request);
}
