package com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;

import java.util.List;

public interface IReviewImageService {
    ReviewImage saveReviewImage(ReviewImage reviewImage);

    void deleteByReviewId(Integer reviewId);

    Integer countByReviewId(Integer reviewId);

    List<ReviewImage> findAllByReviewId(Integer reviewId);
}
