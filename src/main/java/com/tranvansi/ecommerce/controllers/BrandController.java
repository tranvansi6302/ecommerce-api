package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.brands.CreateBrandRequest;
import com.tranvansi.ecommerce.dtos.responses.brans.BrandResponse;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.brands.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/brands")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandService brandService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(
            @RequestBody @Valid CreateBrandRequest request) {
        BrandResponse brandResponse = brandService.createBrand(request);
        ApiResponse<BrandResponse> response = ApiResponse.<BrandResponse>builder()
                .result(brandResponse)
                .message(Message.CREATE_BRAND_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

}
