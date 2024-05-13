package com.tranvansi.ecommerce.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String id;
    @JsonProperty("full_name")
    private String fullName;
    private String avatar;
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Integer blocked;
    private List<AddressResponse> addresses;
}
