package com.tranvansi.ecommerce.dtos.responses.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private Integer id;
    @JsonProperty("full_name")
    private String fullName;
    private String email;
}
