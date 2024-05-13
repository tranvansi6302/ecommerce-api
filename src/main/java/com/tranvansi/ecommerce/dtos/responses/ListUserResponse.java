package com.tranvansi.ecommerce.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListUserResponse {
    private List<UserResponse> result;
    private PaginationResponse pagination;
}
