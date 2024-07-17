package com.tranvansi.ecommerce.modules.reviewmanagements.services;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;
import com.tranvansi.ecommerce.modules.reviewmanagements.repositories.ReviewImageRepository;
import com.tranvansi.ecommerce.modules.reviewmanagements.services.interfaces.IReviewImageService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageService implements IReviewImageService {
    private final ReviewImageRepository reviewImageRepository;

    @Override
    public ReviewImage saveReviewImage(ReviewImage reviewImage) {
        return reviewImageRepository.save(reviewImage);
    }

    @Override
    public void deleteByReviewId(Integer reviewId) {
        reviewImageRepository.deleteByReviewId(reviewId);
    }

    @Override
    public Integer countByReviewId(Integer reviewId) {
        return reviewImageRepository.countByReviewId(reviewId);
    }

    @Override
    public List<ReviewImage> findAllByReviewId(Integer reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId);
    }
}
