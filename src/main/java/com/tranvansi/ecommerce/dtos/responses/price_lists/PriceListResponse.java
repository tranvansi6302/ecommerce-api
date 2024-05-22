package com.tranvansi.ecommerce.dtos.responses.price_lists;

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
public class PriceListResponse {
    @JsonProperty("sale_price")
    private Double salePrice;

    @JsonProperty("variant_id")
    private Integer variantId;

    private String sku;

    @JsonProperty("effective_date")
    private LocalDateTime effectiveDate;
}
