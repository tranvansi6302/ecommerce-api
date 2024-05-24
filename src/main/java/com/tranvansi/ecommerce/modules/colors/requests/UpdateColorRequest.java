package com.tranvansi.ecommerce.modules.colors.requests;

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
public class UpdateColorRequest {
    @NotBlank(message = "INVALID_COLOR_NAME_REQUIRED")
    @Size(min = 3, max = 10, message = "INVALID_COLOR_SIZE")
    private String name;

    @NotBlank(message = "INVALID_COLOR_HEX_REQUIRED")
    @Size(min = 7, max = 7, message = "INVALID_COLOR_HEX_SIZE")
    private String hex;
}
