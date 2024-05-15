package com.tranvansi.ecommerce.services.brands;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.requests.brands.UpdateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IBrandService {
    BrandResponse createBrand(CreateBrandRequest request);

    Page<BrandResponse> getAllBrands(PageRequest pageRequest);

    BrandResponse getBrandById(String id);

    BrandResponse updateBrand(String id, UpdateBrandRequest request);
}
