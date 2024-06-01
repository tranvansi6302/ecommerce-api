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
public class CreateSizeRequest {
    @NotBlank(message = "INVALID_SIZE_NAME_REQUIRED")
    @Size(min = 2, max = 2, message = "INVALID_SIZE_MINIMUM")
    private String name;
}