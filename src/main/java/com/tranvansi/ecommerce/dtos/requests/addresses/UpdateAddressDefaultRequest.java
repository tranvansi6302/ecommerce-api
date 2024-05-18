package com.tranvansi.ecommerce.dtos.requests.addresses;

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
    @NotNull(message = "INVALID_IS_DEFAULT_REQUIRED")
    private Integer isDefault;
}
