package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Integer countByProductId(Integer productId);

    List<ProductImage> findByProductId(Integer productId);
}
