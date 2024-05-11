package com.tranvansi.ecommerce.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.tranvansi.ecommerce.dtos.requests.IntrospectRequest;
import com.tranvansi.ecommerce.dtos.requests.LoginRequest;
import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.requests.VerifyTokenRequest;
import com.tranvansi.ecommerce.dtos.responses.IntrospectResponse;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    SignedJWT verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException;
}
