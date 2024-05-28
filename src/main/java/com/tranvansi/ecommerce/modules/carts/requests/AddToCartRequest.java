package com.tranvansi.ecommerce.modules.carts.requests;

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
    private Integer variantId;

    private Integer quantity;
}
