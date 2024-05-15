package com.tranvansi.ecommerce.services.categories;

import com.tranvansi.ecommerce.dtos.requests.categories.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.requests.categories.UpdateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.categories.CategoryResponse;
import com.tranvansi.ecommerce.entities.Category;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.CategoryMapper;
import com.tranvansi.ecommerce.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public Page<CategoryResponse> getAllCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).map(categoryMapper::toCategoryResponse);
    }

    @Override
    public CategoryResponse updateCategory(String id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (categoryRepository.existsByName(request.getName())
                && !category.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }
}
