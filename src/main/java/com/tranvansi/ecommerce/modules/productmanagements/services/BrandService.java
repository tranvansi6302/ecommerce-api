package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.utils.ConvertUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.BrandRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IBrandService;

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
        brand.setSlug(ConvertUtil.toSlug(request.getName()));
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public Page<BrandResponse> getAllBrands(PageRequest pageRequest) {
        return brandRepository.findAll(pageRequest).map(brandMapper::toBrandResponse);
    }

    @Override
    public BrandResponse getBrandById(Integer id) {
        Brand brand = findBrandById(id);
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(Integer id, UpdateBrandRequest request) {
        Brand brand = findBrandById(id);

        if (brandRepository.existsByName(request.getName())
                && !brand.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        brandMapper.updateBrand(brand, request);
        brand.setSlug(ConvertUtil.toSlug(request.getName()));
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(Integer id) {
        Brand brand = findBrandById(id);
        brandRepository.delete(brand);
    }

    @Override
    public Brand findBrandById(Integer id) {
        return brandRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }
}
