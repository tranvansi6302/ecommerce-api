package com.tranvansi.ecommerce.modules.carts.responses;

import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailResponse {
    private Integer id;
    private Integer quantity;
    private VariantResponse variant;
}
