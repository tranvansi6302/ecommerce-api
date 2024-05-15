package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ICategoryService {
    @PreAuthorize("hasRole('ADMIN')")
    CategoryResponse createCategory(CreateCategoryRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<CategoryResponse> getAllCategories(PageRequest pageRequest);
}
