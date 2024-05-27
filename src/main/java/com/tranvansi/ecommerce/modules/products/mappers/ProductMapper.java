package com.tranvansi.ecommerce.modules.products.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.brands.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.categories.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse;

@Mapper(
        componentModel = "spring",
        uses = {VariantMapper.class, BrandMapper.class, CategoryMapper.class})
public interface ProductMapper {
    ProductDetailResponse toProductDetailResponse(Product product);

    CreateProductResponse toProductResponse(Product product);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProduct(@MappingTarget Product product, UpdateProductRequest request);

    Product createProduct(CreateProductRequest request);
}
