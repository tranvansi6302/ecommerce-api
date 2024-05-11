package com.tranvansi.ecommerce.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VerifyTokenRequest {
    @NotBlank(message = "INVALID_TOKEN_REQUIRED")
    private String token;
}
