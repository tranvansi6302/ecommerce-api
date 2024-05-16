package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.sizes.ISizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final ISizeService sizeService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<SizeResponse>> createSize(@RequestBody @Valid CreateSizeRequest request) {
        SizeResponse sizeResponse = sizeService.createSize(request);
        ApiResponse<SizeResponse> response = ApiResponse.<SizeResponse>builder()
                .result(sizeResponse)
                .message(Message.CREATE_SIZE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
