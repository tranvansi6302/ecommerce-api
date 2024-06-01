package com.tranvansi.ecommerce.modules.reviews.services;

import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.reviews.entities.Review;
import com.tranvansi.ecommerce.modules.reviews.requests.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    Page<ReviewResponse> getAllReviews(PageRequest pageRequest, Specification<Review> specification);
}
