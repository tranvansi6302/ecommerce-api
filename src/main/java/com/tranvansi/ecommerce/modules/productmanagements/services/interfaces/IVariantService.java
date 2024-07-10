package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteManyVariantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateVariantRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IVariantService {
    Variant findVariantById(Integer id);

    Variant saveVariant(Variant variant);

    Page<VariantResponse> getAllVariants(
            PageRequest pageRequest, Specification<Variant> specification);

    VariantResponse updateVariant(Integer id, UpdateVariantRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteManyVariants(DeleteManyVariantRequest request);
}
