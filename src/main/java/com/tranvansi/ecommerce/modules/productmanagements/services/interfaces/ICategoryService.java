package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Category;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CategoryResponse;

public interface ICategoryService {
    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse createCategory(CreateCategoryRequest request);

    Page<CategoryResponse> getAllCategories(
            PageRequest pageRequest, Specification<Category> specification);

    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteCategory(Integer id);

    CategoryResponse getCategoryById(Integer id);

    Category findCategoryById(Integer id);
}
