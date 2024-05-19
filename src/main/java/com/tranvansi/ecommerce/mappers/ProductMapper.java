package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.requests.products.UpdateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.CreateProductResponse;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product createProduct(CreateProductRequest request);

    CreateProductResponse toProductResponse(Product product);

    ProductResponse toProductDetailResponse(Product product);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProduct(@MappingTarget Product product, UpdateProductRequest request);
}
