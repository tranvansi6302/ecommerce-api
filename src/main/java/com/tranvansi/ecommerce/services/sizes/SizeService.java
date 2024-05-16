package com.tranvansi.ecommerce.services.sizes;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.requests.sizes.UpdateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import com.tranvansi.ecommerce.entities.Size;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.SizeMapper;
import com.tranvansi.ecommerce.repositories.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        Size size = sizeMapper.toSize(request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public Page<SizeResponse> getAllSizes(PageRequest pageRequest) {
        return sizeRepository.findAll(pageRequest).map(sizeMapper::toSizeResponse);
    }

    @Override
    public SizeResponse getSizeById(String id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
        return sizeMapper.toSizeResponse(size);
    }

    @Override
    public SizeResponse updateSize(String id, UpdateSizeRequest request) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
        if (sizeRepository.existsByName(request.getName())
                && !size.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.SIZE_ALREADY_EXISTS);
        }
        sizeMapper.updateSize(size, request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }
}
