package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantResponse;
import com.tranvansi.ecommerce.enums.Message;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import com.tranvansi.ecommerce.services.products.IVariantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${api.prefix}/variants")
@RequiredArgsConstructor
@Slf4j
public class VariantController {
    private final IVariantService variantService;

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
}
