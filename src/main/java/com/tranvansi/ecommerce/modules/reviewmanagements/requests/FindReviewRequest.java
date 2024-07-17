package com.tranvansi.ecommerce.modules.reviewmanagements.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindReviewRequest {
    @JsonProperty("variant_id")
    @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
    private Integer variantId;

    @JsonProperty("user_id")
    @NotNull(message = "INVALID_USER_ID_REQUIRED")
    private Integer userId;

    @JsonProperty("order_id")
    @NotNull(message = "INVALID_ORDER_ID_REQUIRED")
    private Integer orderId;
}
