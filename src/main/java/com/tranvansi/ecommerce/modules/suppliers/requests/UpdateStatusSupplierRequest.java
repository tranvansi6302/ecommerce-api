package com.tranvansi.ecommerce.modules.suppliers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.common.enums.SupplierStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
