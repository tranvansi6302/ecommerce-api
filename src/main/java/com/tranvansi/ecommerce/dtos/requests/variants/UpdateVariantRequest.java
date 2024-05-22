package com.tranvansi.ecommerce.dtos.requests.variants;

import jakarta.validation.constraints.NotNull;

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
    @NotNull(message = "INVALID_COLOR_ID_REQUIRED")
    @JsonProperty("color_id")
    private Integer colorId;

    @NotNull(message = "INVALID_SIZE_ID_REQUIRED")
    @JsonProperty("size_id")
    private Integer sizeId;
}
