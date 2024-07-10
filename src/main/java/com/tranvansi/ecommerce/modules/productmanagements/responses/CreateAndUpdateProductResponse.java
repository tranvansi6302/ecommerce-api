package com.tranvansi.ecommerce.modules.productmanagements.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAndUpdateProductResponse {
    private Integer id;
    private String name;
    private String description;
    private String sku;
    private CategoryResponse category;

    private BrandResponse brand;

    private List<VariantResponse> variants;
}
