package com.tranvansi.ecommerce.modules.reviews.services;

import java.util.ArrayList;
import java.util.List;

import com.tranvansi.ecommerce.modules.reviews.requests.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.repositories.ProductRepository;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.reviews.entities.Review;
import com.tranvansi.ecommerce.modules.reviews.entities.ReviewImage;
import com.tranvansi.ecommerce.modules.reviews.mappers.ReviewMapper;
import com.tranvansi.ecommerce.modules.reviews.repositories.ReviewImageRepository;
import com.tranvansi.ecommerce.modules.reviews.repositories.ReviewRepository;
import com.tranvansi.ecommerce.modules.reviews.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviews.responses.ReviewResponse;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Product product =
                productRepository
                        .findById(request.getProductId())
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        Variant variant =
                variantRepository
                        .findById(request.getVariantId())
                        .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
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
            ReviewImage savedImage = reviewImageRepository.save(image);
            imageResponses.add(reviewMapper.toReviewImageResponse(savedImage));
        }
        ReviewResponse reviewResponse = reviewMapper.toReviewResponse(savedReview);
        reviewResponse.setReviewImages(imageResponses);
        return reviewResponse;
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Integer reviewId, UpdateReviewRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
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
            reviewImageRepository.deleteByReviewId(reviewId);
            for (UpdateReviewRequest.ReviewImageRequest reviewImage : request.getReviewImages()) {
                ReviewImage image =
                        ReviewImage.builder().url(reviewImage.getUrl()).review(savedReview).build();
                ReviewImage savedImage = reviewImageRepository.save(image);
                imageResponses.add(reviewMapper.toReviewImageResponse(savedImage));
            }
        }
        ReviewResponse reviewResponse = reviewMapper.toReviewResponse(savedReview);
        reviewResponse.setReviewImages(imageResponses);
        return reviewResponse;
    }

    @Override
    public void deleteReview(Integer reviewId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
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
    public Page<ReviewResponse> getAllReviews(PageRequest pageRequest, Specification<Review> specification) {
        return reviewRepository.findAll(specification, pageRequest).map(reviewMapper::toReviewResponse);
    }
}
