package com.tranvansi.ecommerce.modules.brands.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.brands.entities.Brand;
import com.tranvansi.ecommerce.modules.brands.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.brands.responses.BrandResponse;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand createBrand(CreateBrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, UpdateBrandRequest request);
}
