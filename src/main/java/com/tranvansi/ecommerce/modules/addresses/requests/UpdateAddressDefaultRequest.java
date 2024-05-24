package com.tranvansi.ecommerce.modules.addresses.requests;

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
public class UpdateAddressDefaultRequest {
    @JsonProperty("is_default")
    @NotNull(message = "INVALID_ADDRESS_IS_DEFAULT_REQUIRED")
    private Integer isDefault;
}
