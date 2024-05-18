package com.tranvansi.ecommerce.services.authentication;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.tranvansi.ecommerce.dtos.requests.authentication.*;
import com.tranvansi.ecommerce.dtos.responses.authentication.IntrospectResponse;
import com.tranvansi.ecommerce.dtos.responses.authentication.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.authentication.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    void verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException;

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
