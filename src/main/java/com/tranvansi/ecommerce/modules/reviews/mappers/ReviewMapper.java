package com.tranvansi.ecommerce.modules.reviews.mappers;

import com.tranvansi.ecommerce.modules.reviews.requests.UpdateReviewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.modules.reviews.entities.Review;
import com.tranvansi.ecommerce.modules.reviews.entities.ReviewImage;
import com.tranvansi.ecommerce.modules.reviews.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviews.responses.ReviewResponse;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "variant.productId", source = "variant.product.id")
    @Mapping(target = "reviewImages", ignore = true)
    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "product.id", ignore = true)
    @Mapping(target = "variant.id", ignore = true)
    Review createReview(CreateReviewRequest request);

    ReviewResponse.ReviewImageResponse toReviewImageResponse(ReviewImage reviewImage);

    void updateReview(@MappingTarget Review review, UpdateReviewRequest request);
}
