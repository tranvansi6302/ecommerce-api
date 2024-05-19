package com.tranvansi.ecommerce.dtos.requests.products;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OriginalPriceRequest {
    @NotNull(message = "INVALID_PRICE_REQUIRED")
    private Double price;
}
