package com.tranvansi.ecommerce.modules.reviewmanagements.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Integer id;
    private Integer rating;
    private String comment;

    @JsonProperty("order_id")
    private Integer orderId;

    private VariantResponse variant;

    private UserResponse user;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("review_images")
    private List<ReviewImageResponse> reviewImages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewImageResponse {
        private String url;
    }
}
