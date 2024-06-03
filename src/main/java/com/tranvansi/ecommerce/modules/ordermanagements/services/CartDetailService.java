package com.tranvansi.ecommerce.modules.ordermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.CartDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.CartDetailRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.ICartDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartDetailService implements ICartDetailService {
    private final CartDetailRepository cartDetailRepository;

    @Override
    public boolean existsByVariantIdAndCart(Integer variantId, Cart cart) {
        return cartDetailRepository.existsByVariantIdAndCart(variantId, cart);
    }

    @Override
    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail findCartDetailById(Integer id) {
        return cartDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_DETAIL_NOT_FOUND));
    }

    @Override
    public Page<CartDetail> findAllByCart(Cart cart, Pageable pageable) {
        return cartDetailRepository.findAllByCart(cart, pageable);
    }

    @Override
    public void deleteCartDetail(CartDetail cartDetail) {
        cartDetailRepository.delete(cartDetail);
    }

    @Override
    public CartDetail findCartDetailByVariantIdAndCart(Integer variantId, Cart cart) {
        return cartDetailRepository
                .findByVariantIdAndCart(variantId, cart)
                .orElseThrow(() -> new AppException(ErrorCode.CART_DETAIL_NOT_FOUND));
    }
}
