package com.tranvansi.ecommerce.dtos.responses.authentication;

import com.tranvansi.ecommerce.dtos.responses.users.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private UserResponse user;
}
