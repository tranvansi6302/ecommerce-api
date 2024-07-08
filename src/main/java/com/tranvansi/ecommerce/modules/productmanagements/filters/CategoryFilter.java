package com.tranvansi.ecommerce.modules.productmanagements.filters;

import com.tranvansi.ecommerce.components.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryFilter {
    private String search;
    private CategoryStatus status;
}
