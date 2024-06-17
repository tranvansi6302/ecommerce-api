package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

public interface IVariantService {
    Variant findVariantById(Integer id);

    boolean existsBySku(String sku);

    Variant saveVariant(Variant variant);

    Page<VariantResponse> getAllVariants(
            PageRequest pageRequest, Specification<Variant> specification);
}
