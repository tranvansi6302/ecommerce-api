package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByName(String name);
    boolean existsByHex(String hex);
}