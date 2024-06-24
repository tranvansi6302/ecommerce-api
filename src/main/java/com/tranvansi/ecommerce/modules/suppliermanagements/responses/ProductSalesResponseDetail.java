package com.tranvansi.ecommerce.modules.suppliermanagements.responses;

import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesResponseDetail {
    private Integer id;
    private ProductDetailResponse product;
}
