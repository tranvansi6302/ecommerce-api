package com.tranvansi.ecommerce.modules.productmanagements.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantResponse {
    private Integer id;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    private String sku;

    private ColorResponse color;

    private SizeResponse size;

    private WarehousePurchasePriceAndAvailableQuantity warehouse;
}
