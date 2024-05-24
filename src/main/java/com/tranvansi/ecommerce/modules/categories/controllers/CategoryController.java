package com.tranvansi.ecommerce.modules.categories.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.categories.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.requests.UpdateCategoryRequest;
import com.tranvansi.ecommerce.modules.categories.responses.CategoryResponse;
import com.tranvansi.ecommerce.modules.categories.services.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<CategoryResponse>>> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<CategoryResponse> categoryResponses = categoryService.getAllCategories(pageRequest);
        PagedResponse<List<CategoryResponse>> response =
                BuildResponse.buildPagedResponse(categoryResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder().result(categoryResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .result(categoryResponse)
                        .message(Message.CREATE_CATEGORY_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Integer id, @RequestBody @Valid UpdateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, request);
        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .result(categoryResponse)
                        .message(Message.UPDATE_CATEGORY_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_CATEGORY_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}