package com.tranvansi.ecommerce.modules.reviewmanagements.requests;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewRequest {
    @NotNull(message = "INVALID_REVIEW_RATING_REQUIRED")
    private Integer rating;

    @NotNull(message = "INVALID_REVIEW_COMMENT_REQUIRED")
    private String comment;

    @NotNull(message = "INVALID_ORDER_ID_REQUIRED")
    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("product_id")
    @NotNull(message = "INVALID_PRODUCT_ID_REQUIRED")
    private Integer productId;

    @JsonProperty("variant_id")
    @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
    private Integer variantId;

}
