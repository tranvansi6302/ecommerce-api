package com.tranvansi.ecommerce.modules.reviews.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.reviews.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByVariantIdAndUserId(Integer variantId, Integer userId);
}
