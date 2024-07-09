package com.tranvansi.ecommerce.modules.productmanagements.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.tranvansi.ecommerce.components.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;

    private String sku;

    @JsonProperty("pending_update")
    private Integer pendingUpdate;

    private CategoryResponse category;

    private BrandResponse brand;

    private ProductStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("product_images")
    private List<ProductImageResponse> productImages;
}
