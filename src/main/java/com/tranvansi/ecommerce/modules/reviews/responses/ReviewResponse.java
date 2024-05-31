package com.tranvansi.ecommerce.modules.reviews.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;

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
    private VariantResponse variant;

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
