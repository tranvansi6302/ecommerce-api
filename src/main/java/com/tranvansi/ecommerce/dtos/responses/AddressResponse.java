package com.tranvansi.ecommerce.dtos.responses;

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
public class AddressResponse {
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String description;
    @JsonProperty("is_default")
    private Boolean isDefault;

}
