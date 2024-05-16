package com.tranvansi.ecommerce.dtos.requests.sizes;

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
public class UpdateSizeRequest {
    @NotBlank(message = "INVALID_SIZE_NAME_REQUIRED")
    @Size(max = 5, message = "INVALID_SIZE_MAX")
    private String name;

    @NotBlank(message = "INVALID_SIZE_SUMMARY_REQUIRED")
    private String summary;
}
