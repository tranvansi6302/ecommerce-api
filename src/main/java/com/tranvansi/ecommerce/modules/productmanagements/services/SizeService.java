package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Size;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.SizeMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.SizeRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateSizeRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateSizeRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.SizeResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.ISizeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeService implements ISizeService {
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public SizeResponse createSize(CreateSizeRequest request) {
        if (sizeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SIZE_ALREADY_EXISTS);
        }
        Size size = sizeMapper.createSize(request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public Page<SizeResponse> getAllSizes(PageRequest pageRequest) {
        return sizeRepository.findAll(pageRequest).map(sizeMapper::toSizeResponse);
    }

    @Override
    public SizeResponse getSizeById(Integer id) {
        Size size = findSizeById(id);
        return sizeMapper.toSizeResponse(size);
    }

    @Override
    public SizeResponse updateSize(Integer id, UpdateSizeRequest request) {
        Size size = findSizeById(id);
        if (sizeRepository.existsByName(request.getName())
                && !size.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.SIZE_ALREADY_EXISTS);
        }
        sizeMapper.updateSize(size, request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public void deleteSize(Integer id) {
        Size size = findSizeById(id);
        sizeRepository.delete(size);
    }

    @Override
    public Size findSizeById(Integer id) {
        return sizeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
    }
}
