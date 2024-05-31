package com.tranvansi.ecommerce.modules.reviews.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.reviews.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviews.responses.ReviewResponse;

public interface IReviewService {

    @PreAuthorize("hasRole('USER')")
    ReviewResponse createReview(CreateReviewRequest request);
}
