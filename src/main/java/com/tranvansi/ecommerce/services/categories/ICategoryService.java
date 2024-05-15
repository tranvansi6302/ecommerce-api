package com.tranvansi.ecommerce.services.categories;

import com.tranvansi.ecommerce.dtos.requests.categories.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.requests.categories.UpdateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.categories.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ICategoryService {
    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse createCategory(CreateCategoryRequest request);

    Page<CategoryResponse> getAllCategories(PageRequest pageRequest);

    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse updateCategory(String id, UpdateCategoryRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteCategory(String id);

    CategoryResponse getCategoryById(String id);
}
