package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.requests.brands.UpdateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import com.tranvansi.ecommerce.entities.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand createBrand(CreateBrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, UpdateBrandRequest request);
}
