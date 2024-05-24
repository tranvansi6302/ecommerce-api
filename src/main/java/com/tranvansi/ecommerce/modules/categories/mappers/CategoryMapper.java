package com.tranvansi.ecommerce.modules.categories.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.categories.entities.Category;
import com.tranvansi.ecommerce.modules.categories.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.responses.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category createCategory(CreateCategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, UpdateCategoryRequest request);
}
