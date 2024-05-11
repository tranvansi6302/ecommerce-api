package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.responses.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.services.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody @Valid RegisterRequest request) {
        RegisterResponse user = authenticationService.register(request);
        ApiResponse<RegisterResponse> response = ApiResponse.<RegisterResponse>builder()
                .message("Đăng ký tài khoản thành công")
                .result(user)
                .build();
        return ResponseEntity.ok(response);
    }
}
