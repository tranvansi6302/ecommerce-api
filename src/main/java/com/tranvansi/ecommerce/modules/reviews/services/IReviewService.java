package com.tranvansi.ecommerce.modules.reviews.services;

import com.tranvansi.ecommerce.modules.reviews.requests.UpdateReviewRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.reviews.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviews.responses.ReviewResponse;

public interface IReviewService {

    @PreAuthorize("hasRole('USER')")
    ReviewResponse createReview(CreateReviewRequest request);

    @PreAuthorize("hasRole('USER')")
    ReviewResponse updateReview(Integer reviewId, UpdateReviewRequest request);

    @PreAuthorize("hasRole('USER')")
    void deleteReview(Integer reviewId);

    ReviewResponse getReviewById(Integer reviewId);
}
