package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.colors.IColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/colors")
@RequiredArgsConstructor
public class ColorController {
    private final IColorService colorService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(@RequestBody @Valid CreateColorRequest request) {
        ColorResponse colorResponse = colorService.createColor(request);
        ApiResponse<ColorResponse> response = ApiResponse.<ColorResponse>builder()
                .result(colorResponse)
                .message(Message.CREATE_COLOR_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);

    }
}
