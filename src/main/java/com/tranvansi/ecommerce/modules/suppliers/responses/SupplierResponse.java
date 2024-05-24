package com.tranvansi.ecommerce.modules.suppliers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.common.enums.SupplierStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private Integer id;

    private String name;

    @JsonProperty("tax_code")
    private String taxCode;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private SupplierStatus status;
}
