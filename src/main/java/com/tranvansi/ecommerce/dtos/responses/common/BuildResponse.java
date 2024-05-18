package com.tranvansi.ecommerce.dtos.responses.common;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BuildResponse {
    public static <T> PagedResponse<List<T>> buildPagedResponse(
            Page<T> pageData, Pageable pageable) {
        return PagedResponse.<List<T>>builder()
                .result(pageData.getContent())
                .pagination(
                        PaginationResponse.builder()
                                .page(pageable.getPageNumber() + 1)
                                .limit(pageable.getPageSize())
                                .totalPage(pageData.getTotalPages())
                                .build())
                .build();
    }
}
