package com.tranvansi.ecommerce.modules.productmanagements.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantWarehouseResponse {
    private Integer id;

    @JsonProperty("variant_name")
    private String variantName;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;

    private String sku;

    private String color;

    private String size;

    private WarehousePurchasePriceAndAvailableQuantity warehouse;

    @JsonProperty("price_plans")
    private List<PricePlanResponse> pricePlans;
}
