package com.tranvansi.ecommerce.services.variants;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.variants.CreateVariantResponse;
import com.tranvansi.ecommerce.dtos.responses.variants.UpdateVariantResponse;

public interface IVariantService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    CreateVariantResponse createVariant(Integer productId, CreateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    UpdateVariantResponse updateVariant(Integer variantId, UpdateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteVariant(Integer variantId);
}
