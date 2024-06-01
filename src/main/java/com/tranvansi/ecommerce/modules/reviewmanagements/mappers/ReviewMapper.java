package com.tranvansi.ecommerce.modules.reviewmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.Review;
import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.CreateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.requests.UpdateReviewRequest;
import com.tranvansi.ecommerce.modules.reviewmanagements.responses.ReviewResponse;

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
