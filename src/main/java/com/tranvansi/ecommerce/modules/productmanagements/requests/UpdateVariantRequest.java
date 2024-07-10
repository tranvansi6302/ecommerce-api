package com.tranvansi.ecommerce.modules.productmanagements.requests;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVariantRequest {
    @JsonProperty("variant_name")
    @NotBlank(message = "INVALID_VARIANT_NAME_REQUIRED")
    private String variantName;

    @NotBlank(message = "INVALID_VARIANT_SKU_REQUIRED")
    private String sku;

    @NotBlank(message = "INVALID_COLOR_REQUIRED")
    private String color;

    @NotBlank(message = "INVALID_SIZE_REQUIRED")
    private String size;
}
