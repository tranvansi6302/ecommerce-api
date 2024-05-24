package com.tranvansi.ecommerce.modules.brands.requests;

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
public class CreateBrandRequest {
    @NotBlank(message = "INVALID_BRAND_NAME_REQUIRED")
    @Size(min = 3, max = 50, message = "INVALID_BRAND_SIZE")
    private String name;

    private String summary;
}
