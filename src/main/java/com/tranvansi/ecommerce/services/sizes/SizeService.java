package com.tranvansi.ecommerce.services.sizes;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import com.tranvansi.ecommerce.entities.Size;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.SizeMapper;
import com.tranvansi.ecommerce.repositories.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeService implements ISizeService{
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public SizeResponse createSize(CreateSizeRequest request) {
        if(sizeRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SIZE_ALREADY_EXISTS);
        }
        Size size = sizeMapper.toSize(request);
        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }
}
