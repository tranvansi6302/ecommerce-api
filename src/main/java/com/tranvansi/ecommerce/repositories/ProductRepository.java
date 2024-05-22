package com.tranvansi.ecommerce.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.Product;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);

    void deleteByIsDeletedAndDeletedAtBefore(Integer isDeleted, LocalDateTime deletedAt);

    Page<Product> findAllByIsDeleted(Integer isDeleted, Pageable pageable);
}
