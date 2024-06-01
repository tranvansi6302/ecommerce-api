package com.tranvansi.ecommerce.modules.reviews.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.reviews.entities.ReviewImage;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
    void deleteByReviewId(Integer reviewId);
}
