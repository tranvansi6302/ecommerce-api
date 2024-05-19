package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.categories.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.requests.categories.UpdateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.categories.CategoryResponse;
import com.tranvansi.ecommerce.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category createCategory(CreateCategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, UpdateCategoryRequest request);
}
