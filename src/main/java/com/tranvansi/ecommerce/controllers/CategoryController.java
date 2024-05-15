package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.CreateCategoryRequest;
import com.tranvansi.ecommerce.dtos.responses.*;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;


    @GetMapping("")
    public ResponseEntity<PagedResponse<List<CategoryResponse>>> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction
    ) {
        Sort sort = Sort.by("createdAt");
        sort = sort_direction.equalsIgnoreCase("asc")
                ? sort.ascending() : sort.descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<CategoryResponse> categoryResponses = categoryService.getAllCategories(pageRequest);
        PagedResponse<List<CategoryResponse>> response = PagedResponse.<List<CategoryResponse>>builder()
                .result(categoryResponses.getContent())
                .pagination(PaginationResponse.builder()
                        .page(page)
                        .limit(limit)
                        .totalPage(categoryResponses.getTotalPages())
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

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

//@GetMapping("")
//public ResponseEntity<PagedResponse<List<CategoryResponse>>> getAllCategories(
//        @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
//) {
//    Page<CategoryResponse> categoryResponses = categoryService.getAllCategories(pageable);
//    PagedResponse<List<CategoryResponse>> response = buildPagedResponse(categoryResponses, pageable);
//    return ResponseEntity.ok(response);
//}
//
//private <T> PagedResponse<List<T>> buildPagedResponse(Page<T> pageData, Pageable pageable) {
//    return PagedResponse.<List<T>>builder()
//            .result(pageData.getContent())
//            .pagination(PaginationResponse.builder()
//                    .page(pageable.getPageNumber() + 1)
//                    .limit(pageable.getPageSize())
//                    .totalPage(pageData.getTotalPages())
//                    .build())
//            .build();
//}