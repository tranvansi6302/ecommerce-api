package com.tranvansi.ecommerce.modules.usermanagements.requests;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAddressRequest {
    @NotNull(message = "INVALID_ADDRESS_ID_REQUIRED")
    @JsonProperty("address_id")
    private Integer addressId;
}
