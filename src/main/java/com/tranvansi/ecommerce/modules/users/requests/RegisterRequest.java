package com.tranvansi.ecommerce.modules.users.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_USER_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_USER_FULL_NAME_MIN_LENGTH")
    private String fullName;

    @NotBlank(message = "INVALID_USER_EMAIL_REQUIRED")
    @Email(message = "INVALID_USER_EMAIL_FORMAT")
    private String email;

    @NotBlank(message = "INVALID_USER_PASSWORD_REQUIRED")
    @Size(min = 6, message = "INVALID_USER_PASSWORD_MIN_LENGTH")
    @Pattern(regexp = "\\S+", message = "INVALID_USER_PASSWORD_WHITESPACE")
    private String password;
}
