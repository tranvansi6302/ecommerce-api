package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Category;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category createCategory(CreateCategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, UpdateCategoryRequest request);
}
