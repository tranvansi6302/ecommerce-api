package com.tranvansi.ecommerce.dtos.responses.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String province;
    private String district;
    private String ward;
    private String description;

    @JsonProperty("is_default")
    private Integer isDefault;
}
