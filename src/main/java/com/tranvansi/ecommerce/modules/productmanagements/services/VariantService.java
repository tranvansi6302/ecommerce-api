package com.tranvansi.ecommerce.modules.productmanagements.services;

import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteManyVariantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateVariantRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public VariantResponse updateVariant(Integer id, UpdateVariantRequest request) {
        Variant variant = findVariantById(id);
        if (variantRepository.existsBySkuAndIdNot(request.getSku(), id)) {
            throw new AppException(ErrorCode.VARIANT_SKU_ALREADY_EXISTS);
        }
        if (variantRepository.existsByVariantNameAndIdNot(request.getVariantName(), id)) {
            throw new AppException(ErrorCode.VARIANT_NAME_ALREADY_EXISTS);
        }
        if (variantRepository.existsByColorAndSizeAndIdNotAndProductId(
                request.getColor(), request.getSize(), id, variant.getProduct().getId())) {
            throw new AppException(ErrorCode.VARIANT_COLOR_SIZE_ALREADY_EXISTS);
        }
        variant.setVariantName(request.getVariantName());
        variant.setSku(request.getSku());
        variant.setColor(request.getColor());
        variant.setSize(request.getSize());
        return variantMapper.toVariantResponse(saveVariant(variant));
    }

    @Transactional
    @Override
    public void deleteManyVariants(DeleteManyVariantRequest request) {
        for (Integer id : request.getVariantIds()) {
            Variant variant = findVariantById(id);
            variantRepository.delete(variant);
        }
    }
}
