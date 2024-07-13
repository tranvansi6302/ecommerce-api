package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteManyVariantRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateVariantRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

public interface IVariantService {
    Variant findVariantById(Integer id);

    Variant saveVariant(Variant variant);

    Page<VariantResponse> getAllVariants(
            PageRequest pageRequest, Specification<Variant> specification);

    VariantResponse updateVariant(Integer id, UpdateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteManyVariants(DeleteManyVariantRequest request);
}
