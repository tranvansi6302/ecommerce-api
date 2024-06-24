package com.tranvansi.ecommerce.modules.usermanagements.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.usermanagements.requests.ForgotPasswordRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.LoginRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.RegisterRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.ResetPasswordRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.LoginResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.RegisterResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/oauth2/google")
    public ResponseEntity<ApiResponse<LoginResponse>> googleLogin(
            @RequestParam("code") String code) {
        LoginResponse loginResponse = authenticationService.googleLogin(code);
        ApiResponse<LoginResponse> response =
                ApiResponse.<LoginResponse>builder()
                        .message(Message.LOGIN_SUCCESS.getMessage())
                        .result(loginResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody @Valid RegisterRequest request) {
        RegisterResponse registerResponse = authenticationService.register(request);
        ApiResponse<RegisterResponse> response =
                ApiResponse.<RegisterResponse>builder()
                        .message(Message.REGISTER_SUCCESS.getMessage())
                        .result(registerResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request) {
        LoginResponse loginResponse = authenticationService.login(request);
        ApiResponse<LoginResponse> response =
                ApiResponse.<LoginResponse>builder()
                        .message(Message.LOGIN_SUCCESS.getMessage())
                        .result(loginResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.FORGOT_PASSWORD_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.RESET_PASSWORD_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
