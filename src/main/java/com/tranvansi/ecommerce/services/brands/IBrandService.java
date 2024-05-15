package com.tranvansi.ecommerce.services.brands;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;

public interface IBrandService {
    BrandResponse createBrand(CreateBrandRequest request);
}
