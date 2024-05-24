package com.tranvansi.ecommerce.modules.suppliers.controllers;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;
import com.tranvansi.ecommerce.modules.suppliers.services.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final ISupplierService supplierService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<SupplierResponse>> createSupplier(
            @RequestBody @Valid CreateSupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.createSupplier(request);
        ApiResponse<SupplierResponse> response =
                ApiResponse.<SupplierResponse>builder()
                        .result(supplierResponse)
                        .message(Message.SUPPLIER_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

}
