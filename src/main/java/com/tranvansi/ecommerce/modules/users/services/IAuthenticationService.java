package com.tranvansi.ecommerce.modules.users.services;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.tranvansi.ecommerce.modules.users.requests.*;
import com.tranvansi.ecommerce.modules.users.responses.IntrospectResponse;
import com.tranvansi.ecommerce.modules.users.responses.LoginResponse;
import com.tranvansi.ecommerce.modules.users.responses.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    void verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException;

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
