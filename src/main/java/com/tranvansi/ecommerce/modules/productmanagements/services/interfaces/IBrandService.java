package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateManyStatusBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;

public interface IBrandService {
    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse createBrand(CreateBrandRequest request);

    Page<BrandResponse> getAllBrands(PageRequest pageRequest, Specification<Brand> specification);

    BrandResponse getBrandById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    BrandResponse updateBrand(Integer id, UpdateBrandRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteBrand(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    void updateManyStatusBrand(UpdateManyStatusBrandRequest request);

    Brand findBrandById(Integer name);
}
