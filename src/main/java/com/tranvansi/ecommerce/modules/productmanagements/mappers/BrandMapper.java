package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateBrandRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand createBrand(CreateBrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, UpdateBrandRequest request);
}
