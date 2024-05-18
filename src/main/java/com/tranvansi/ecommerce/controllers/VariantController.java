package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.products.UpdateVariantRequest;
import com.tranvansi.ecommerce.dtos.responses.products.VariantResponse;
import com.tranvansi.ecommerce.services.products.IVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/variants")
@RequiredArgsConstructor
@Slf4j
public class VariantController {
    private final IVariantService variantService;
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateVariant(
            @PathVariable Integer id, @RequestBody @Valid UpdateVariantRequest request) {
        variantService.updateVariant(id, request);
        ApiResponse<VariantResponse> response =
                ApiResponse.<VariantResponse>builder()
                        .message("Cập nhật biến thể thành công")
                        .build();
        return ResponseEntity.ok(response);
    }
}
