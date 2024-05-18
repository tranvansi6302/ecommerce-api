package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.requests.products.UpdateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    Product toProduct(CreateProductRequest request);


    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    void updateProductFromRequest(@MappingTarget Product product, UpdateProductRequest request);
}
