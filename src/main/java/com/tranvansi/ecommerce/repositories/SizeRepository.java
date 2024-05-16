package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    boolean existsByName(String name);
}