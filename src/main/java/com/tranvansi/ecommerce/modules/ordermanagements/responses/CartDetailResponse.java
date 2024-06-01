package com.tranvansi.ecommerce.modules.ordermanagements.responses;

import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

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
