package com.tranvansi.ecommerce.modules.productmanagements.filters;

import com.tranvansi.ecommerce.components.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilter {
    private String productName;
    private String categorySlug;
    private String brandSlug;
    private Double priceMin;
    private Double priceMax;
    private Integer ratingMin;
    private ProductStatus status;
}
