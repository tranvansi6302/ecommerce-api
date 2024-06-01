package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByName(String name);

    boolean existsByHex(String hex);
}
