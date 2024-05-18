package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
    void deleteByIsDeletedAndDeletedAtBefore(Integer isDeleted, LocalDateTime deletedAt);
}