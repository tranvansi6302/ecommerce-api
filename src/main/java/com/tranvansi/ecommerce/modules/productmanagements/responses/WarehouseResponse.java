package com.tranvansi.ecommerce.modules.productmanagements.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private VariantWarehouseResponse variant;
}
