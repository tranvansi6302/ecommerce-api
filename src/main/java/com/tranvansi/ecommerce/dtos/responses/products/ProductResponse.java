package com.tranvansi.ecommerce.dtos.responses.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import com.tranvansi.ecommerce.dtos.responses.categories.CategoryResponse;
import com.tranvansi.ecommerce.entities.Brand;
import com.tranvansi.ecommerce.entities.Category;
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

    @JsonProperty("is_delete")
    private Integer isDeleted;

    private CategoryResponse category;

    private BrandResponse brand;
}
