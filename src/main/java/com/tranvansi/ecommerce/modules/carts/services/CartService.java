package com.tranvansi.ecommerce.modules.carts.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import com.tranvansi.ecommerce.modules.carts.mappers.CartMapper;
import com.tranvansi.ecommerce.modules.carts.repositories.CartDetailRepository;
import com.tranvansi.ecommerce.modules.carts.repositories.CartRepository;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.carts.responses.CartResponse;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;
import com.tranvansi.ecommerce.modules.warehouses.repositories.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;
    private final CartDetailRepository cartDetailRepository;
    private final WarehouseRepository warehouseRepository;

    private final CartMapper cartMapper;
    private final VariantMapper variantMapper;

    @Override
    @Transactional
    public CartResponse addProductToCart(AddToCartRequest request) {
        Variant variant =
                variantRepository
                        .findById(request.getVariantId())
                        .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        if (!warehouseRepository.existsByVariant(variant)) {
            throw new AppException(ErrorCode.WAREHOUSE_VARIANT_NOT_FOUND);
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!cartRepository.existsByUserId(user.getId())) {
            Cart cart = Cart.builder().user(user).build();
            cartRepository.save(cart);
        }
        Cart existingCart = cartRepository.findByUserId(user.getId()).orElseThrow(null);

        if (cartDetailRepository.existsByVariantIdAndCart(request.getVariantId(), existingCart)) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_IN_CART);
        }

        CartDetail cartDetail = cartMapper.addProductToCart(request);
        cartDetail.setVariant(variant);
        cartDetail.setCart(existingCart);
        cartDetailRepository.save(cartDetail);

        CartDetailResponse toCartDetailResponse = cartMapper.toCartDetailResponse(cartDetail);

        CartResponse response = cartMapper.addToCartResponse(existingCart);
        response.setCartDetail(toCartDetailResponse);

        return response;
    }

    @Override
    @Transactional
    public CartResponse updateProductFromCart(Integer cartDetailId, UpdateCartRequest request) {
        CartDetail cartDetail =
                cartDetailRepository
                        .findById(cartDetailId)
                        .orElseThrow(() -> new AppException(ErrorCode.CART_DETAIL_NOT_FOUND));
        cartMapper.updateProductFromCart(cartDetail, request);
        cartDetailRepository.save(cartDetail);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Cart existingCart = cartRepository.findByUserId(user.getId()).orElseThrow(null);

        cartMapper.updateProductFromCart(cartDetail, request);

        CartDetailResponse toCartDetailResponse = cartMapper.toCartDetailResponse(cartDetail);

        Variant variant =
                variantRepository
                        .findById(cartDetail.getVariant().getId())
                        .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        toCartDetailResponse.setVariant(variantMapper.toVariantResponse(variant));
        CartResponse response = cartMapper.addToCartResponse(existingCart);
        response.setCartDetail(toCartDetailResponse);

        return response;
    }

    @Override
    public Page<CartDetailResponse> getAllProductFromCarts(PageRequest pageRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart =
                cartRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        return cartDetailRepository
                .findAllByCart(cart, pageRequest)
                .map(cartMapper::toCartDetailResponse);
    }

    @Override
    public void deleteProductFromCart(Integer cartDetailId) {
        CartDetail cartDetail =
                cartDetailRepository
                        .findById(cartDetailId)
                        .orElseThrow(() -> new AppException(ErrorCode.CART_DETAIL_NOT_FOUND));
        cartDetailRepository.delete(cartDetail);
    }
}
