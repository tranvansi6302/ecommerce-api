package com.tranvansi.ecommerce.modules.users.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "INVALID_USER_EMAIL_REQUIRED")
    @Email(message = "INVALID_USER_EMAIL_FORMAT")
    private String email;

    @NotBlank(message = "INVALID_USER_PASSWORD_REQUIRED")
    private String password;
}
