package com.tranvansi.ecommerce.modules.reviews.services;

import java.util.ArrayList;
import java.util.List;

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
}
