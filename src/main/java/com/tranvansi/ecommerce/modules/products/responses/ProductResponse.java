package com.tranvansi.ecommerce.modules.products.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;

    @JsonProperty("pending_update")
    private Integer pendingUpdate;


    private CategoryResponse category;

    private BrandResponse brand;
}
