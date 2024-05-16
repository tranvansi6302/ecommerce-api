package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
}