package com.tranvansi.ecommerce.dtos.requests.addresses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAddressRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_FULL_NAME_MIN_LENGTH")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}", message = "INVALID_PHONE_NUMBER_FORMAT")
    private String phoneNumber;

    @NotBlank(message = "INVALID_PROVINCE_REQUIRED")
    private String province;

    @NotBlank(message = "INVALID_DISTRICT_REQUIRED")
    private String district;

    @NotBlank(message = "INVALID_WARD_REQUIRED")
    private String ward;

    @NotBlank(message = "INVALID_DESCRIPTION_REQUIRED")
    private String description;

    private User user;
}
