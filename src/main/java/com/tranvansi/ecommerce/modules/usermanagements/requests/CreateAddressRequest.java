package com.tranvansi.ecommerce.modules.usermanagements.requests;

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
public class CreateAddressRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "INVALID_USER_FULL_NAME_REQUIRED")
    @Size(min = 4, message = "INVALID_USER_FULL_NAME_MIN_LENGTH")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_USER_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}", message = "INVALID_USER_PHONE_NUMBER_FORMAT")
    private String phoneNumber;

    @NotBlank(message = "INVALID_ADDRESS_PROVINCE_REQUIRED")
    private String province;

    @NotBlank(message = "INVALID_ADDRESS_DISTRICT_REQUIRED")
    private String district;

    @NotBlank(message = "INVALID_ADDRESS_WARD_REQUIRED")
    private String ward;

    @NotBlank(message = "INVALID_ADDRESS_DESCRIPTION_REQUIRED")
    private String description;

    @NotNull(message = "INVALID_ADDRESS_PROVINCE_ID_REQUIRED")
    @JsonProperty("province_id")
    private Integer provinceId;

    @NotNull(message = "INVALID_ADDRESS_DISTRICT_ID_REQUIRED")
    @JsonProperty("district_id")
    private Integer districtId;

    @NotBlank(message = "INVALID_ADDRESS_WARD_ID_REQUIRED")
    @JsonProperty("ward_id")
    private String wardId;
}
