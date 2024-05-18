package com.tranvansi.ecommerce.services.products;

import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;

public interface IVariantService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    VariantResponse createVariant(Integer productId, CreateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void updateVariant(Integer variantId, UpdateVariantRequest request);

}
