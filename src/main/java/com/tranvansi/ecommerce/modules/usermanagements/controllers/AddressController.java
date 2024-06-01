package com.tranvansi.ecommerce.modules.usermanagements.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IAddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class AddressController {
    private final IAddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @RequestBody @Valid CreateAddressRequest request) {
        AddressResponse addressResponse = addressService.createAddress(request);
        ApiResponse<AddressResponse> response =
                ApiResponse.<AddressResponse>builder()
                        .message(Message.CREATE_ADDRESS_SUCCESS.getMessage())
                        .result(addressResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddressDefault(
            @PathVariable Integer id, @RequestBody @Valid UpdateAddressDefaultRequest request) {
        AddressResponse addressResponse = addressService.updateAddressDefault(id, request);
        ApiResponse<AddressResponse> response =
                ApiResponse.<AddressResponse>builder()
                        .message(Message.UPDATE_ADDRESS_DEFAULT_SUCCESS.getMessage())
                        .result(addressResponse)
                        .build();
        return ResponseEntity.ok(response);
    }
}
