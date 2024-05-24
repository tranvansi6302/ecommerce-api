package com.tranvansi.ecommerce.modules.products.responses;

import java.util.List;

import com.tranvansi.ecommerce.modules.brands.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.categories.responses.CategoryResponse;

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

    private List<VariantResponse> variants;
}
