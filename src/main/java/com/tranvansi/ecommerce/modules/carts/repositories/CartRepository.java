package com.tranvansi.ecommerce.modules.carts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.carts.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsByUserId(Integer userId);

    Optional<Cart> findByUserId(Integer userId);
}
