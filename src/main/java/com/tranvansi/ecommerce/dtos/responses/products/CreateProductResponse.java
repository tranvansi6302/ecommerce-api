package com.tranvansi.ecommerce.dtos.responses.products;

import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import com.tranvansi.ecommerce.dtos.responses.categories.CategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResponse {
    private Integer id;
    private String name;
    private String description;

    private CategoryResponse category;

    private BrandResponse brand;
}
