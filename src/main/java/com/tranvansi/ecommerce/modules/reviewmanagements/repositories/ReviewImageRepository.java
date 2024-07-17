package com.tranvansi.ecommerce.modules.reviewmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.ReviewImage;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
    void deleteByReviewId(Integer reviewId);

    Integer countByReviewId(Integer reviewId);

    List<ReviewImage> findAllByReviewId(Integer reviewId);
}
