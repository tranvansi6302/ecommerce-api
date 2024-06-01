package com.tranvansi.ecommerce.modules.ordermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Cart;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.CartDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.mappers.CartMapper;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.CartRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.requests.UpdateCartRequest;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.CartResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.ICartDetailService;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.ICartService;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IWarehouseService;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final IVariantService variantService;
    private final ICartDetailService cartDetailService;
    private final IWarehouseService warehouseService;

    private final AuthUtil authUtil;
    private final CartMapper cartMapper;
    private final VariantMapper variantMapper;

    @Override
    @Transactional
    public CartResponse addProductToCart(AddToCartRequest request) {
        Variant variant = variantService.findVariantById(request.getVariantId());
        if (warehouseService.existsByVariant(variant)) {
            throw new AppException(ErrorCode.WAREHOUSE_VARIANT_NOT_FOUND);
        }

        User user = authUtil.getUser();

        if (!cartRepository.existsByUserId(user.getId())) {
            Cart cart = Cart.builder().user(user).build();
            cartRepository.save(cart);
        }
        Cart existingCart = cartRepository.findByUserId(user.getId()).orElseThrow(null);

        if (cartDetailService.existsByVariantIdAndCart(request.getVariantId(), existingCart)) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_IN_CART);
        }

        CartDetail cartDetail = cartMapper.addProductToCart(request);
        cartDetail.setVariant(variant);
        cartDetail.setCart(existingCart);
        cartDetailService.saveCartDetail(cartDetail);

        CartDetailResponse toCartDetailResponse = cartMapper.toCartDetailResponse(cartDetail);

        CartResponse response = cartMapper.addToCartResponse(existingCart);
        response.setCartDetail(toCartDetailResponse);

        return response;
    }

    @Override
    @Transactional
    public CartResponse updateProductFromCart(Integer cartDetailId, UpdateCartRequest request) {
        CartDetail cartDetail = cartDetailService.findCartDetailById(cartDetailId);
        cartMapper.updateProductFromCart(cartDetail, request);
        cartDetailService.saveCartDetail(cartDetail);

        User user = authUtil.getUser();

        Cart existingCart = findByUserId(user.getId());

        cartMapper.updateProductFromCart(cartDetail, request);

        CartDetailResponse toCartDetailResponse = cartMapper.toCartDetailResponse(cartDetail);

        Variant variant = variantService.findVariantById(cartDetail.getVariant().getId());

        toCartDetailResponse.setVariant(variantMapper.toVariantResponse(variant));
        CartResponse response = cartMapper.addToCartResponse(existingCart);
        response.setCartDetail(toCartDetailResponse);

        return response;
    }

    @Override
    public Page<CartDetailResponse> getAllProductFromCarts(PageRequest pageRequest) {
        User user = authUtil.getUser();
        Cart cart =
                cartRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        return cartDetailService
                .findAllByCart(cart, pageRequest)
                .map(cartMapper::toCartDetailResponse);
    }

    @Override
    public void deleteProductFromCart(Integer cartDetailId) {
        CartDetail cartDetail = cartDetailService.findCartDetailById(cartDetailId);
        cartDetailService.deleteCartDetail(cartDetail);
    }

    @Override
    public Cart findByUserId(Integer userId) {
        return cartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
    }
}
