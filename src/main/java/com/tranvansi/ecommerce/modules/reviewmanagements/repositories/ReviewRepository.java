package com.tranvansi.ecommerce.modules.reviewmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.reviewmanagements.entities.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository
        extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    boolean existsByVariantIdAndUserId(Integer variantId, Integer userId);

    @Query("SELECT SUM(r.rating) FROM Review r WHERE r.product.id = :productId")
    Integer findTotalStarsByProductId(Integer productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    Integer findReviewCountByProductId(Integer productId);

    List<Review> findAllByProductId(Integer productId);

    Optional<Review> findByVariantIdAndUserIdAndOrderId(Integer variantId, Integer userId, Integer orderId);




}
