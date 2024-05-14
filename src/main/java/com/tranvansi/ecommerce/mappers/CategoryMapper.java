package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.CategoryResponse;
import com.tranvansi.ecommerce.entities.Address;
import com.tranvansi.ecommerce.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CreateCategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
}
