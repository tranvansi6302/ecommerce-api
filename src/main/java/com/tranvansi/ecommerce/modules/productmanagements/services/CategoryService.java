package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.utils.ConvertUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Category;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.CategoryRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CategoryResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.ICategoryService;

import lombok.RequiredArgsConstructor;

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
        Category category = categoryMapper.createCategory(request);
        category.setSlug(ConvertUtil.toSlug(request.getName()));
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryResponse> getAllCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).map(categoryMapper::toCategoryResponse);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        Category category = findCategoryById(id);
        if (categoryRepository.existsByName(request.getName())
                && !category.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        categoryMapper.updateCategory(category, request);
        category.setSlug(ConvertUtil.toSlug(request.getName()));
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = findCategoryById(id);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}