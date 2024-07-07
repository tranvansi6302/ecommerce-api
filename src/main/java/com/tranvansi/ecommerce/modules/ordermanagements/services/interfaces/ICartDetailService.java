package com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.CartDetail;

public interface ICartDetailService {

    void saveCartDetail(CartDetail cartDetail);

    CartDetail findCartDetailById(Integer id);

    Page<CartDetail> findAllByCart(Cart cart, Pageable pageable);

    void deleteCartDetail(CartDetail cartDetail);

    CartDetail findCartDetailByVariantIdAndCart(Integer variantId, Cart cart);

    boolean existsById(Integer id);
}
