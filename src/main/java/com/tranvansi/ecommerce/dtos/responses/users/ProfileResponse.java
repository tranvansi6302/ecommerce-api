package com.tranvansi.ecommerce.dtos.responses.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tranvansi.ecommerce.dtos.responses.addresses.AddressResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
    "id",
    "full_name",
    "avatar",
    "email",
    "date_of_birth",
    "phone_number",
    "blocked",
    "roles"
})
public class ProfileResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String avatar;
    private String email;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private Integer blocked;
    private List<AddressResponse> addresses;
}
