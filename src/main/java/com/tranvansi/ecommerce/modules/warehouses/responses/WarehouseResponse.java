package com.tranvansi.ecommerce.modules.warehouses.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    private Integer id;

    @JsonProperty("total_quantity")
    private Integer totalQuantity;

    @JsonProperty("available_quantity")
    private Integer availableQuantity;

    private String sku;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

    private VariantResponse variant;
}
