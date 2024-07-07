package com.tranvansi.ecommerce.modules.ordermanagements.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Integer id;

    @JsonProperty("cart_detail")
    private CartDetailResponse cartDetail;
}
