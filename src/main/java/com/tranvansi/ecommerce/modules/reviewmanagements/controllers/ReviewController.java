package com.tranvansi.ecommerce.modules.reviewmanagements.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tranvansi.ecommerce.components.constants.FileConstant;
import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.components.utils.FileUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.reviewmanagements.filters.ReviewFilter;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UpdateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UploadReviewImagesRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.responses.ReviewResponse;
import com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces.IReviewService;
import com.tranvansi.ecommerce.modules.reviewmanagements.specifications.ReviewSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<ReviewResponse>>> getAllReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "rating", required = false) Integer rating,
            @RequestParam(name = "name", required = false) String productName,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        ReviewFilter filter =
                ReviewFilter.builder().rating(rating).productName(productName).build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ReviewResponse> reviewResponses =
                reviewService.getAllReviews(pageRequest, new ReviewSpecification(filter));
        PagedResponse<List<ReviewResponse>> response =
                BuildResponse.buildPagedResponse(reviewResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(
            @PathVariable Integer reviewId) {
        ReviewResponse reviewResponse = reviewService.getReviewById(reviewId);
        ApiResponse<ReviewResponse> apiResponse =
                ApiResponse.<ReviewResponse>builder().result(reviewResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Integer reviewId, @RequestBody @Valid UpdateReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.updateReview(reviewId, request);
        ApiResponse<ReviewResponse> apiResponse =
                ApiResponse.<ReviewResponse>builder()
                        .result(reviewResponse)
                        .message(Message.UPDATE_REVIEW_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        ApiResponse<Void> apiResponse =
                ApiResponse.<Void>builder()
                        .message(Message.DELETE_REVIEW_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}/upload-images")
    public ResponseEntity<ApiResponse<ReviewResponse>> uploadReviewImages(
            @PathVariable Integer id, @RequestPart("files") List<MultipartFile> files) {
        UploadReviewImagesRequest request =
                UploadReviewImagesRequest.builder().files(files).build();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REVIEW_IMAGE_REQUIRED);
            }
            if (!FileUtil.isImageFile(file)) {
                throw new AppException(ErrorCode.INVALID_REVIEW_IMAGE_FORMAT);
            }
            if (file.getSize() > FileConstant.MAX_FILE_SIZE_MB) { // 5MB
                throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
            }
        }
        ReviewResponse reviewResponse = reviewService.uploadReviewImages(id, request);
        ApiResponse<ReviewResponse> response =
                ApiResponse.<ReviewResponse>builder()
                        .result(reviewResponse)
                        .message(Message.UPLOAD_REVIEW_IMAGE_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
