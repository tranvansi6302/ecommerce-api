package com.tranvansi.ecommerce.modules.carts.services;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.carts.entities.Cart;
import com.tranvansi.ecommerce.modules.carts.entities.CartDetail;
import com.tranvansi.ecommerce.modules.carts.mappers.CartMapper;
import com.tranvansi.ecommerce.modules.carts.repositories.CartDetailRepository;
import com.tranvansi.ecommerce.modules.carts.repositories.CartRepository;
import com.tranvansi.ecommerce.modules.carts.requests.AddToCartRequest;
import com.tranvansi.ecommerce.modules.carts.responses.AddToCartResponse;
import com.tranvansi.ecommerce.modules.carts.responses.CartDetailResponse;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;
import com.tranvansi.ecommerce.modules.warehouses.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;
    private final CartMapper cartMapper;
    private final CartDetailRepository cartDetailRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public AddToCartResponse addToCart(AddToCartRequest request) {
        Variant variant = variantRepository.findById(request.getVariantId()).orElseThrow(
                () -> new AppException(ErrorCode.VARIANT_NOT_FOUND)
        );
        if(warehouseRepository.existsByVariant(variant)) {
            throw new AppException(ErrorCode.WAREHOUSE_VARIANT_NOT_FOUND);
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(!cartRepository.existsByUserId(user.getId())) {
            Cart cart = Cart
                    .builder()
                    .user(user)
                    .build();
            cartRepository.save(cart);
        }
        Cart existingCart = cartRepository.findByUserId(user.getId()).orElseThrow(null);

        if(cartDetailRepository.existsByVariantIdAndCart(request.getVariantId(), existingCart)) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_IN_CART);
        }

        CartDetail cartDetail = cartMapper.addToCart(request);
        cartDetail.setVariant(variant);
        cartDetail.setCart(existingCart);
        cartDetailRepository.save(cartDetail);


        CartDetailResponse cartDetailResponse = cartMapper.cartDetailResponse(cartDetail);

        AddToCartResponse response = cartMapper.addToCartResponse(existingCart);
        response.setCartDetail(cartDetailResponse);


        return response;

    }
}
