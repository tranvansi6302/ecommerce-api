package com.tranvansi.ecommerce.services;

import com.nimbusds.jose.JOSEException;
import com.tranvansi.ecommerce.dtos.requests.*;
import com.tranvansi.ecommerce.dtos.responses.IntrospectResponse;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    void verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException;

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
