package com.tranvansi.ecommerce.modules.suppliermanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdateStatusSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.SupplierResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.ISupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final ISupplierService supplierService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<SupplierResponse>>> getAllSuppliers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<SupplierResponse> supplierResponses = supplierService.getAllSuppliers(pageRequest);
        PagedResponse<List<SupplierResponse>> response =
                BuildResponse.buildPagedResponse(supplierResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> getSupplierById(@PathVariable Integer id) {
        SupplierResponse supplierResponse = supplierService.getSupplierById(id);
        ApiResponse<SupplierResponse> response =
                ApiResponse.<SupplierResponse>builder().result(supplierResponse).build();
        return ResponseEntity.ok(response);
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> updateSupplier(
            @PathVariable Integer id, @RequestBody @Valid UpdateSupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.updateSupplier(id, request);
        ApiResponse<SupplierResponse> response =
                ApiResponse.<SupplierResponse>builder()
                        .result(supplierResponse)
                        .message(Message.SUPPLIER_UPDATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SupplierResponse>> deleteSoftOrRestoreSupplier(
            @PathVariable Integer id, @RequestBody @Valid UpdateStatusSupplierRequest request) {
        SupplierResponse supplierResponse =
                supplierService.deleteSoftOrRestoreSupplier(id, request);
        ApiResponse<SupplierResponse> response =
                ApiResponse.<SupplierResponse>builder()
                        .result(supplierResponse)
                        .message(Message.SUPPLIER_UPDATED_STATUS_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
