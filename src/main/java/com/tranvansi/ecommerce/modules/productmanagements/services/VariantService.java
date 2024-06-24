package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariantService implements IVariantService {
    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;

    @Override
    public Variant findVariantById(Integer id) {
        return variantRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
    }

    @Override
    public Variant saveVariant(Variant variant) {
        return variantRepository.save(variant);
    }

    @Override
    public Page<VariantResponse> getAllVariants(
            PageRequest pageRequest, Specification<Variant> specification) {
        return variantRepository
                .findAll(specification, pageRequest)
                .map(variantMapper::toVariantResponse);
    }
}
