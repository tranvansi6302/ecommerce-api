package com.tranvansi.ecommerce.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_FULL_NAME_MIN_LENGTH")
    private String fullName;

    @NotBlank(message = "INVALID_EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL_FORMAT")
    private String email;

    @JsonProperty("date_of_birth")
    @NotBlank(message = "INVALID_DATE_OF_BIRTH_REQUIRED")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "INVALID_DATE_OF_BIRTH_FORMAT")
    private String dateOfBirth;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}",
            message = "INVALID_PHONE_NUMBER_FORMAT")
    private String phoneNumber;

    @NotNull(message = "INVALID_BLOCKED_REQUIRED")
    private Integer blocked;

    @NotEmpty(message = "INVALID_ROLES_REQUIRED")
    List<String> roles;
}
