package com.tranvansi.ecommerce.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "INVALID_TOKEN_REQUIRED")
    private String token;

    @NotBlank(message = "INVALID_PASSWORD_REQUIRED")
    @Size(min = 6, message = "INVALID_PASSWORD_LENGTH")
    @Pattern(regexp = "\\S+", message = "INVALID_PASSWORD_WHITESPACE")
    private String password;
}
