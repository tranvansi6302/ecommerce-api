package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.CategoryResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .message(Message.CREATE_CATEGORY_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
