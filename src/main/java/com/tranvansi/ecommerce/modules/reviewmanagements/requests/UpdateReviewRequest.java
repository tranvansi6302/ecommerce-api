package com.tranvansi.ecommerce.modules.reviewmanagements.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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
public class UpdateReviewRequest {
    @NotNull(message = "INVALID_REVIEW_RATING_REQUIRED")
    private Integer rating;

    @NotNull(message = "INVALID_REVIEW_COMMENT_REQUIRED")
    private String comment;

    @JsonProperty("review_images")
    List<ReviewImageRequest> reviewImages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewImageRequest {
        @NotBlank(message = "INVALID_REVIEW_IMAGE_URL_REQUIRED")
        private String url;
    }
}
