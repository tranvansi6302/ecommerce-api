package com.tranvansi.ecommerce.modules.usermanagements.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthUserResponse {
    private String id;

    private String email;

    private String name;

    private Boolean verifiedEmail;

    private String givenName;

    private String familyName;

    private String picture;
}
