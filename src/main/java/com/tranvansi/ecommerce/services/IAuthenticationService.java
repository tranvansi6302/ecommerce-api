package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);
}
