package com.tranvansi.ecommerce.modules.carts.repositories;

import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    Optional<CartDetail> findByCart(Cart cart);
    boolean existsByVariantIdAndCart(Integer variantId, Cart cart);
}