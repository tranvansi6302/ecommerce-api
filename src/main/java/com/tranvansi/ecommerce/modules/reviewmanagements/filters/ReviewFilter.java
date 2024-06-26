package com.tranvansi.ecommerce.modules.reviewmanagements.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFilter {
    private Integer rating;
    private String productName;
}
