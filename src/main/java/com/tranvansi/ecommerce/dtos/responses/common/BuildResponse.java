package com.tranvansi.ecommerce.dtos.responses.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BuildResponse {
    public  static  <T> PagedResponse<List<T>> buildPagedResponse(
            Page<T> pageData, Pageable pageable) {
        return PagedResponse.<List<T>>builder()
                .result(pageData.getContent())
                .pagination(PaginationResponse.builder()
                        .page(pageable.getPageNumber() + 1)
                        .limit(pageable.getPageSize())
                        .totalPage(pageData.getTotalPages())
                        .build())
                .build();
    }
}
