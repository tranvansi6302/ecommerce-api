package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Brand;

@Repository
public interface BrandRepository
        extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {
    boolean existsByName(String name);
}
