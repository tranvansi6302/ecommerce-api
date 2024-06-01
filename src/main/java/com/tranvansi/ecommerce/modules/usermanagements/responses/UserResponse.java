package com.tranvansi.ecommerce.modules.usermanagements.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tranvansi.ecommerce.common.enums.UserStatus;

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
public class UserResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String avatar;
    private String email;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private UserStatus status;
    private List<RoleResponse> roles;
}
