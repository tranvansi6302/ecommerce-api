package com.tranvansi.ecommerce.modules.productmanagements.services;

import com.tranvansi.ecommerce.components.enums.BrandStatus;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateManyStatusBrandRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.utils.ConvertUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.BrandRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IBrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        Brand brand = brandMapper.createBrand(request);
        brand.setStatus(BrandStatus.ACTIVE);
        brand.setSlug(ConvertUtil.toSlug(request.getName()));
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public Page<BrandResponse> getAllBrands(
            PageRequest pageRequest, Specification<Brand> specification) {
        return brandRepository
                .findAll(specification, pageRequest)
                .map(brandMapper::toBrandResponse);
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
    public void updateManyStatusBrand(UpdateManyStatusBrandRequest request) {
        for (Integer id : request.getBrandIds()) {
            Brand brand = findBrandById(id);
            if (brand.getStatus().equals(BrandStatus.ACTIVE)) {
                brand.setStatus(BrandStatus.INACTIVE);
            } else {
                brand.setStatus(BrandStatus.ACTIVE);

            }
            brandRepository.save(brand);
        }
    }

    @Override
    public Brand findBrandById(Integer id) {
        return brandRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }
}
