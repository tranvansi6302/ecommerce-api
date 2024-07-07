package com.tranvansi.ecommerce.modules.usermanagements.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "INVALID_USER_CURRENT_PASSWORD_REQUIRED")
    @Size(min = 6, message = "INVALID_USER_PASSWORD_MIN_LENGTH")
    @Pattern(regexp = "\\S+", message = "INVALID_USER_PASSWORD_WHITESPACE")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank(message = "INVALID_USER_NEW_PASSWORD_REQUIRED")
    @Size(min = 6, message = "INVALID_USER_PASSWORD_MIN_LENGTH")
    @Pattern(regexp = "\\S+", message = "INVALID_USER_PASSWORD_WHITESPACE")
    @JsonProperty("new_password")
    private String newPassword;
}
