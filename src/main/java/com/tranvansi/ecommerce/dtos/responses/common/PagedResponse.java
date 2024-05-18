package com.tranvansi.ecommerce.dtos.responses.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {
    @Builder.Default private int code = 1000;

    private T result;
    private PaginationResponse pagination;
}
