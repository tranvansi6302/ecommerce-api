package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "pendingUpdate", ignore = true)
    @Mapping(target = "isDelete", ignore = true)
    Product toProduct(CreateProductRequest request);

    @Mapping(target = "category_id", source = "category.id")
    @Mapping(target = "brand_id", source = "brand.id")
    @Mapping(target = "pending_update", source = "pendingUpdate")
    ProductResponse toProductResponse(Product product);
}
