package com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.Review;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UpdateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UploadReviewImagesRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.responses.ReviewResponse;

public interface IReviewService {

    @PreAuthorize("hasRole('USER')")
    ReviewResponse createReview(CreateReviewRequest request);

    @PreAuthorize("hasRole('USER')")
    ReviewResponse updateReview(Integer reviewId, UpdateReviewRequest request);

    @PreAuthorize("hasRole('USER')")
    void deleteReview(Integer reviewId);

    @PreAuthorize("hasRole('USER')")
    ReviewResponse uploadReviewImages(Integer reviewId, UploadReviewImagesRequest request);

    ReviewResponse getReviewById(Integer reviewId);

    Page<ReviewResponse> getAllReviews(
            PageRequest pageRequest, Specification<Review> specification);
}
