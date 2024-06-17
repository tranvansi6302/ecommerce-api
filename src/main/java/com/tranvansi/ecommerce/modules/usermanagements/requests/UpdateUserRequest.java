package com.tranvansi.ecommerce.modules.usermanagements.requests;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_USER_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_USER_FULL_NAME_MIN_LENGTH")
    private String fullName;

    @NotBlank(message = "INVALID_USER_EMAIL_REQUIRED")
    @Email(message = "INVALID_USER_EMAIL_FORMAT")
    private String email;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "INVALID_USER_DATE_OF_BIRTH_REQUIRED")
    private LocalDateTime dateOfBirth;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_USER_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}", message = "INVALID_USER_PHONE_NUMBER_FORMAT")
    private String phoneNumber;

    @NotNull(message = "INVALID_USER_STATUS_REQUIRED")
    private UserStatus status;

    @NotEmpty(message = "INVALID_ROLES_REQUIRED")
    List<String> roles;
}
