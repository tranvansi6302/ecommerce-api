package com.tranvansi.ecommerce.modules.reviews.controllers;

import com.tranvansi.ecommerce.modules.reviews.requests.UpdateReviewRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.reviews.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviews.responses.ReviewResponse;
import com.tranvansi.ecommerce.modules.reviews.services.IReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @RequestBody @Valid CreateReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.createReview(request);
        ApiResponse<ReviewResponse> apiResponse =
                ApiResponse.<ReviewResponse>builder()
                        .result(reviewResponse)
                        .message(Message.CREATE_REVIEW_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Integer reviewId,
            @RequestBody @Valid UpdateReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.updateReview(reviewId, request);
        ApiResponse<ReviewResponse> apiResponse =
                ApiResponse.<ReviewResponse>builder()
                        .result(reviewResponse)
                        .message(Message.UPDATE_REVIEW_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
