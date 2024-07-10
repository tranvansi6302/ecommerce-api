package com.tranvansi.ecommerce.modules.suppliermanagements.requests;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateManyStatusSupplierRequest {

    @NotNull(message = "INVALID_SUPPLIER_IDS_REQUIRED")
    @JsonProperty("supplier_ids")
    private Integer[] supplierIds;
}
