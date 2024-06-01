package com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;

public interface IReviewImageService {
    ReviewImage saveReviewImage(ReviewImage reviewImage);

    void deleteByReviewId(Integer reviewId);
}
