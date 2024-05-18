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
    @Size(min = 2, max = 2, message = "INVALID_SIZE_MINIMUM")
    private String name;

    @NotBlank(message = "INVALID_SIZE_SUMMARY_REQUIRED")
    private String summary;
}
