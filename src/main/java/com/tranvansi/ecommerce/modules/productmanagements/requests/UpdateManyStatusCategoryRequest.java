package com.tranvansi.ecommerce.modules.productmanagements.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateManyStatusCategoryRequest {

    @NotNull(message = "INVALID_CATEGORY_IDS_REQUIRED")
    @JsonProperty("category_ids")
    private Integer[] categoryIds;

}
