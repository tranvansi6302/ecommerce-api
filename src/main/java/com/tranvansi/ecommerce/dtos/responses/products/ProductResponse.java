package com.tranvansi.ecommerce.dtos.responses.products;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("pending_update")
    private Integer pendingUpdate;
    @JsonProperty("is_delete")
    private Integer isDeleted;
    @JsonProperty("category_id")
    private Integer categoryId;
    @JsonProperty("brand_id")
    private Integer brandId;
}
