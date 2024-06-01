package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariantService implements IVariantService {
    private final VariantRepository variantRepository;

    @Override
    public Variant findVariantById(Integer id) {
        return variantRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
    }

    @Override
    public boolean existsBySku(String sku) {
        return variantRepository.existsBySku(sku);
    }

    @Override
    public Variant saveVariant(Variant variant) {
        return variantRepository.save(variant);
    }
}
