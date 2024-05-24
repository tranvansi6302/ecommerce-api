package com.tranvansi.ecommerce.modules.brands.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.brands.entities.Brand;
import com.tranvansi.ecommerce.modules.brands.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.brands.repositories.BrandRepository;
import com.tranvansi.ecommerce.modules.brands.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.responses.BrandResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        Brand brand = brandMapper.createBrand(request);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public Page<BrandResponse> getAllBrands(PageRequest pageRequest) {
        return brandRepository.findAll(pageRequest).map(brandMapper::toBrandResponse);
    }

    @Override
    public BrandResponse getBrandById(Integer id) {
        return brandRepository
                .findById(id)
                .map(brandMapper::toBrandResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }

    @Override
    public BrandResponse updateBrand(Integer id, UpdateBrandRequest request) {
        Brand brand =
                brandRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        if (brandRepository.existsByName(request.getName())
                && !brand.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        brandMapper.updateBrand(brand, request);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(Integer id) {
        Brand brand =
                brandRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brandRepository.delete(brand);
    }
}