package com.tranvansi.ecommerce.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {
    private int page;
    private int limit;

    @JsonProperty("total_page")
    private int totalPage;
}
