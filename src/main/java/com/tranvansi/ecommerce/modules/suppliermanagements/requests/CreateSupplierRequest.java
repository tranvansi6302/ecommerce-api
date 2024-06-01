package com.tranvansi.ecommerce.modules.suppliermanagements.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSupplierRequest {
    @NotBlank(message = "INVALID_SUPPLIER_NAME_REQUIRED")
    private String name;

    @JsonProperty("tax_code")
    @NotBlank(message = "INVALID_SUPPLIER_TAX_CODE_REQUIRED")
    private String taxCode;

    @NotBlank(message = "INVALID_SUPPLIER_EMAIL_REQUIRED")
    @Email(message = "INVALID_SUPPLIER_EMAIL_FORMAT")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "INVALID_SUPPLIER_PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "\\d{10}", message = "INVALID_SUPPLIER_PHONE_NUMBER_FORMAT")
    private String phoneNumber;

    @NotBlank(message = "INVALID_SUPPLIER_ADDRESS_REQUIRED")
    private String address;
}
