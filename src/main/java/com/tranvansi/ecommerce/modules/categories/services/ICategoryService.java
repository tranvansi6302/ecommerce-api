package com.tranvansi.ecommerce.modules.categories.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.categories.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.responses.CategoryResponse;

public interface ICategoryService {
    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse createCategory(CreateCategoryRequest request);

    Page<CategoryResponse> getAllCategories(PageRequest pageRequest);

    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteCategory(Integer id);

    CategoryResponse getCategoryById(Integer id);
}
