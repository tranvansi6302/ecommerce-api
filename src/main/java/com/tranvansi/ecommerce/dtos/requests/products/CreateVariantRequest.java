package com.tranvansi.ecommerce.dtos.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVariantRequest {

    @NotNull(message = "INVALID_SIZE_ID_REQUIRED")
    @JsonProperty("size_id")
    private Integer sizeId;

    @NotNull(message = "INVALID_VARIANT_REQUIRED")
    @Size(min = 1, message = "INVALID_VARIANT_MINIMUM")
    @JsonProperty("variant_details")
    @Valid
    private List<VariantDetailRequest> variantDetailRequests;
}


