package com.tranvansi.ecommerce.services.brands;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import com.tranvansi.ecommerce.entities.Brand;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.BrandMapper;
import com.tranvansi.ecommerce.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService{
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {
        if(brandRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        Brand brand = brandMapper.toBrand(request);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public Page<BrandResponse> getAllBrands(PageRequest pageRequest) {
        return brandRepository.findAll(pageRequest).map(brandMapper::toBrandResponse);
    }
}
