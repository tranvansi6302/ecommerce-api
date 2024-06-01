package com.tranvansi.ecommerce.modules.productmanagements.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {
    @NotBlank(message = "INVALID_CATEGORY_NAME_REQUIRED")
    @Size(min = 3, max = 50, message = "INVALID_CATEGORY_NAME_SIZE")
    private String name;

    private String summary;
}
