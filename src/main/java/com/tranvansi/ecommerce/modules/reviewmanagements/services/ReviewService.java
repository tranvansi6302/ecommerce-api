package com.tranvansi.ecommerce.modules.reviewmanagements.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IProductService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.reviewmanagements.entities.Review;
import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;
import com.tranvansi.ecommerce.modules.reviewmanagements.mappers.ReviewMapper;
import com.tranvansi.ecommerce.modules.reviewmanagements.repositories.ReviewRepository;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UpdateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.responses.ReviewResponse;
import com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces.IReviewImageService;
import com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces.IReviewService;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final IReviewImageService reviewImageService;
    private final IProductService productService;
    private final IVariantService variantService;
    private final AuthUtil authUtil;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        User user = authUtil.getUser();
        Product product = productService.findProductById(request.getProductId());
        Variant variant = variantService.findVariantById(request.getVariantId());
        if (reviewRepository.existsByVariantIdAndUserId(request.getVariantId(), user.getId())) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }
        Review review = reviewMapper.createReview(request);
        review.setProduct(product);
        review.setVariant(variant);
        review.setUser(user);
        Review savedReview = reviewRepository.save(review);

        List<ReviewResponse.ReviewImageResponse> imageResponses = new ArrayList<>();

        for (CreateReviewRequest.ReviewImageRequest reviewImage : request.getReviewImages()) {
            ReviewImage image =
                    ReviewImage.builder().url(reviewImage.getUrl()).review(savedReview).build();
            ReviewImage savedImage = reviewImageService.saveReviewImage(image);
            imageResponses.add(reviewMapper.toReviewImageResponse(savedImage));
        }
        ReviewResponse reviewResponse = reviewMapper.toReviewResponse(savedReview);
        reviewResponse.setReviewImages(imageResponses);
        return reviewResponse;
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Integer reviewId, UpdateReviewRequest request) {
        User user = authUtil.getUser();
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }

        reviewMapper.updateReview(review, request);
        Review savedReview = reviewRepository.save(review);

        List<ReviewResponse.ReviewImageResponse> imageResponses = new ArrayList<>();
        if (request.getReviewImages() != null) {
            reviewImageService.deleteByReviewId(reviewId);
            for (UpdateReviewRequest.ReviewImageRequest reviewImage : request.getReviewImages()) {
                ReviewImage image =
                        ReviewImage.builder().url(reviewImage.getUrl()).review(savedReview).build();
                ReviewImage savedImage = reviewImageService.saveReviewImage(image);
                imageResponses.add(reviewMapper.toReviewImageResponse(savedImage));
            }
        }
        ReviewResponse reviewResponse = reviewMapper.toReviewResponse(savedReview);
        reviewResponse.setReviewImages(imageResponses);
        return reviewResponse;
    }

    @Override
    public void deleteReview(Integer reviewId) {
        User user = authUtil.getUser();
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.delete(review);
    }

    @Override
    public ReviewResponse getReviewById(Integer reviewId) {
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public Page<ReviewResponse> getAllReviews(
            PageRequest pageRequest, Specification<Review> specification) {
        return reviewRepository
                .findAll(specification, pageRequest)
                .map(reviewMapper::toReviewResponse);
    }
}
