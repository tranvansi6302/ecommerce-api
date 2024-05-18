package com.tranvansi.ecommerce.dtos.requests.categories;

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
public class CreateCategoryRequest {
    @NotBlank(message = "INVALID_CATEGORY_NAME_REQUIRED")
    @Size(min = 3, max = 50, message = "INVALID_NAME_SIZE")
    private String name;

    @NotBlank(message = "INVALID_CATEGORY_SUMMARY_REQUIRED")
    private String summary;
}
