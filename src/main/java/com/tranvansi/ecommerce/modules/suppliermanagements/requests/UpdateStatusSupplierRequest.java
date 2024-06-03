package com.tranvansi.ecommerce.modules.suppliermanagements.requests;

import jakarta.validation.constraints.NotNull;

import com.tranvansi.ecommerce.components.enums.SupplierStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusSupplierRequest {
    @NotNull(message = "INVALID_SUPPLIER_STATUS_REQUIRED")
    private SupplierStatus status;
}
