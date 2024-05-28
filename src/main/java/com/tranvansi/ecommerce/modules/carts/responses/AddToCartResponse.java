package com.tranvansi.ecommerce.modules.carts.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.users.responses.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartResponse {
    private Integer id;
    private ProfileResponse user;

    @JsonProperty("cart_detail")
    private CartDetailResponse cartDetail;
}
