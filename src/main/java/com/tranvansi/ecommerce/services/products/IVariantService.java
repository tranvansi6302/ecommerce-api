package com.tranvansi.ecommerce.services.products;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantResponse;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;

public interface IVariantService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    VariantResponse createVariant(Integer productId, CreateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    UpdateVariantResponse updateVariant(Integer variantId, UpdateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteVariant(Integer variantId);
}
