package com.tranvansi.ecommerce.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tranvansi.ecommerce.dtos.requests.authentication.ForgotPasswordRequest;
import com.tranvansi.ecommerce.dtos.requests.authentication.LoginRequest;
import com.tranvansi.ecommerce.dtos.requests.authentication.RegisterRequest;
import com.tranvansi.ecommerce.dtos.requests.authentication.ResetPasswordRequest;
import com.tranvansi.ecommerce.dtos.responses.authentication.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.authentication.RegisterResponse;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.authentication.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

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
