package com.tranvansi.ecommerce.services.brands;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.requests.brands.UpdateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;

public interface IBrandService {
    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse createBrand(CreateBrandRequest request);

    Page<BrandResponse> getAllBrands(PageRequest pageRequest);

    BrandResponse getBrandById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse updateBrand(Integer id, UpdateBrandRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteBrand(Integer id);
}
