package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    boolean existsByName(String name);
}
