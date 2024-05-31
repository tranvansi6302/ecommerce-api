package com.tranvansi.ecommerce.modules.carts.requests;

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
public class AddToCartRequest {
    @JsonProperty("variant_id")
    @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
    private Integer variantId;

    @NotNull(message = "INVALID_CART_QUANTITY")
    private Integer quantity;
}
