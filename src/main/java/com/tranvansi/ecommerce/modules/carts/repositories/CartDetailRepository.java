package com.tranvansi.ecommerce.modules.carts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    boolean existsByVariantIdAndCart(Integer variantId, Cart cart);
}
