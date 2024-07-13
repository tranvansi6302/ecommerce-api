package com.tranvansi.ecommerce.modules.productmanagements.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductImageRequest {
    @NotNull(message = "INVALID_PRODUCT_ID_REQUIRED")
    @JsonProperty("product_id")
    private Integer productId;
}
