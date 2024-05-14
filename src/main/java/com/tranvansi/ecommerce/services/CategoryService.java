package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.CategoryResponse;
import com.tranvansi.ecommerce.entities.Category;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.CategoryMapper;
import com.tranvansi.ecommerce.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
}
