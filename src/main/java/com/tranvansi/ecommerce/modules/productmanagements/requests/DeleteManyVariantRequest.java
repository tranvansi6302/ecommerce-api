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
public class DeleteManyVariantRequest {

    @NotNull(message = "INVALID_VARIANT_IDS_REQUIRED")
    @JsonProperty("variant_ids")
    private Integer[] variantIds;
}
