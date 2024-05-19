package com.tranvansi.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.dtos.requests.products.CreateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.variants.CreateVariantResponse;
import com.tranvansi.ecommerce.dtos.responses.variants.UpdateVariantResponse;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.variants.IVariantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${api.prefix}/variants")
@RequiredArgsConstructor
@Slf4j
public class VariantController {
    private final IVariantService variantService;

    @PostMapping("/{productId}/products")
    public ResponseEntity<ApiResponse<?>> createVariant(
            @PathVariable Integer productId,
            @RequestBody @Valid List<CreateVariantRequest> requests,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            for (FieldError error : bindingResult.getFieldErrors()) {
                String enumKey = Objects.requireNonNull(error.getDefaultMessage());
                ErrorCode errorCode = ErrorCode.INVALID_KEY;
                try {
                    errorCode = ErrorCode.valueOf(enumKey);
                } catch (IllegalArgumentException ex) {
                    log.error("Invalid key: {}", enumKey);
                }
                ApiResponse<?> response =
                        ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();
                return ResponseEntity.status(errorCode.getStatusCode()).body(response);
            }
        }
        List<CreateVariantResponse> variantResponses = new ArrayList<>();
        for (CreateVariantRequest request : requests) {
            CreateVariantResponse variantResponse =
                    variantService.createVariant(productId, request);
            variantResponses.add(variantResponse);
        }

        ApiResponse<List<CreateVariantResponse>> response =
                ApiResponse.<List<CreateVariantResponse>>builder()
                        .message(Message.CREATE_VARIANT_SUCCESS.getMessage())
                        .result(variantResponses)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateVariantResponse>> updateVariant(
            @PathVariable Integer id, @RequestBody @Valid UpdateVariantRequest request) {
        UpdateVariantResponse updateVariantResponse = variantService.updateVariant(id, request);
        ApiResponse<UpdateVariantResponse> response =
                ApiResponse.<UpdateVariantResponse>builder()
                        .result(updateVariantResponse)
                        .message(Message.UPDATE_VARIANT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteVariant(@PathVariable Integer id) {
        variantService.deleteVariant(id);
        ApiResponse<?> response =
                ApiResponse.builder().message(Message.DELETE_VARIANT_SUCCESS.getMessage()).build();
        return ResponseEntity.ok(response);
    }
}
