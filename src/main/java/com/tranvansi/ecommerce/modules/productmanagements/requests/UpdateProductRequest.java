package com.tranvansi.ecommerce.modules.productmanagements.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    @NotBlank(message = "INVALID_PRODUCT_NAME_REQUIRED")
    @Size(min = 3, max = 200, message = "INVALID_PRODUCT_NAME_LENGTH")
    private String name;

    private String description;

    @NotBlank(message = "INVALID_PRODUCT_SKU_REQUIRED")
    private String sku;

    @NotNull(message = "INVALID_CATEGORY_ID_REQUIRED")
    @JsonProperty("category_id")
    private Integer categoryId;

    @NotNull(message = "INVALID_BRAND_ID_REQUIRED")
    @JsonProperty("brand_id")
    private Integer brandId;
}
