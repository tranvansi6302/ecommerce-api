package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.tranvansi.ecommerce.modules.usermanagements.requests.*;
import com.tranvansi.ecommerce.modules.usermanagements.responses.IntrospectResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.LoginResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.RegisterResponse;

public interface IAuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    void verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException;

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    LoginResponse googleLogin(String code);
}
