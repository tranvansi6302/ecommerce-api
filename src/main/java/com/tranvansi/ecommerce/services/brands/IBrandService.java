package com.tranvansi.ecommerce.services.brands;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.requests.brands.UpdateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IBrandService {
    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse createBrand(CreateBrandRequest request);

    Page<BrandResponse> getAllBrands(PageRequest pageRequest);

    BrandResponse getBrandById(String id);

    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse updateBrand(String id, UpdateBrandRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteBrand(String id);
}