package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    boolean existsByName(String name);
}