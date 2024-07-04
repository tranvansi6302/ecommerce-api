package com.tranvansi.ecommerce.modules.usermanagements.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateProfileRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_USER_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_USER_FULL_NAME_MIN_LENGTH")
    private String fullName;



    @JsonProperty("date_of_birth")
    @NotNull(message = "INVALID_USER_DATE_OF_BIRTH_REQUIRED")
    private LocalDateTime dateOfBirth;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_USER_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}", message = "INVALID_USER_PHONE_NUMBER_FORMAT")
    private String phoneNumber;
}
