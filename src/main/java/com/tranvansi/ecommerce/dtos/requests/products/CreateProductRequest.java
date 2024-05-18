package com.tranvansi.ecommerce.dtos.requests.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {
    @NotBlank(message = "INVALID_PRODUCT_NAME_REQUIRED")
    @Size(min = 3, max = 200, message = "INVALID_PRODUCT_NAME_LENGTH")
    private String name;

    private String description;

    // Not validated -> Set value class Service
    @JsonProperty("pending_update")
    private Integer pendingUpdate = ProductStatus.PENDING_UPDATE.getValue();

    // Not validated -> Set value class Service
    @JsonProperty("is_deleted")
    private Integer isDeleted = ProductStatus.NOT_DELETED.getValue();

    @NotNull(message = "INVALID_CATEGORY_ID_REQUIRED")
    @JsonProperty("category_id")
    private Integer categoryId;

    @NotNull(message = "INVALID_BRAND_ID_REQUIRED")
    @JsonProperty("brand_id")
    private Integer brandId;
}
