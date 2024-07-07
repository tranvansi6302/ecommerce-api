package com.tranvansi.ecommerce.modules.usermanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.DeleteAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IAddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class AddressController {
    private final IAddressService addressService;

    @GetMapping("/addresses")
    public ResponseEntity<PagedResponse<List<AddressResponse>>> getMyAddress(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<AddressResponse> addressResponses = addressService.getMyAddress(pageRequest);
        PagedResponse<List<AddressResponse>> response =
                BuildResponse.buildPagedResponse(addressResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

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

    @PatchMapping("/addresses/{id}/default")
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

    @DeleteMapping("/addresses")
    public ResponseEntity<ApiResponse<String>> deleteAddress(
            @RequestBody @Valid DeleteAddressRequest request) {
        addressService.deleteAddress(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_ADDRESS_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
