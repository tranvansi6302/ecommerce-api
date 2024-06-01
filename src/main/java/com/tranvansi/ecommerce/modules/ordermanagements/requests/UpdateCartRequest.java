package com.tranvansi.ecommerce.modules.ordermanagements.requests;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartRequest {
    @NotNull(message = "INVALID_CART_QUANTITY")
    private Integer quantity;
}
