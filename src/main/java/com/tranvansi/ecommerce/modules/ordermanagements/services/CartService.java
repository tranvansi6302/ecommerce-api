package com.tranvansi.ecommerce.modules.ordermanagements.services;

import com.tranvansi.ecommerce.modules.ordermanagements.requests.DeleteManyProductCart;
import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.PricePlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.utils.AuthUtil;
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
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IWarehouseService;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final IVariantService variantService;
    private final ICartDetailService cartDetailService;
    private final IWarehouseService warehouseService;
    private final PricePlanService pricePlanService;
    private final AuthUtil authUtil;
    private final CartMapper cartMapper;
    private final PricePlanMapper pricePlanMapper;
    private final VariantMapper variantMapper;

    @Override
    @Transactional
    public CartResponse addProductToCart(AddToCartRequest request) {
        Variant variant = variantService.findVariantById(request.getVariantId());
        if (warehouseService.existsByVariant(variant)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        User user = authUtil.getUser();

        if (!cartRepository.existsByUserId(user.getId())) {
            Cart cart = Cart.builder().user(user).build();
            this.saveCart(cart);
        }
        Cart existingCart = cartRepository.findByUserId(user.getId()).orElseThrow(null);
        CartDetail existingCartDetail = cartDetailService.findCartDetailByVariantIdAndCart(variant.getId(), existingCart);
        CartDetail cartDetail = new CartDetail();

        CartDetailResponse toCartDetailResponse = null;

        if (existingCartDetail != null) {
            existingCartDetail.setQuantity(existingCartDetail.getQuantity() + request.getQuantity());
            cartDetailService.saveCartDetail(existingCartDetail);
            existingCartDetail.setId(existingCartDetail.getId());
            toCartDetailResponse = cartMapper.toCartDetailResponse(existingCartDetail);
        }else {
            cartDetail.setQuantity(request.getQuantity());
            cartDetail.setVariant(variant);
            cartDetail.setCart(existingCart);
            cartDetailService.saveCartDetail(cartDetail);
            toCartDetailResponse = cartMapper.toCartDetailResponse(cartDetail);
        }


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

    private PricePlan getCurrentPricePlan(Integer variantId) {
        List<PricePlan> pricePlans =
                pricePlanService.findByVariantIdOrderByStartDateDesc(variantId);
        LocalDateTime now = LocalDateTime.now();

        for (PricePlan pricePlan : pricePlans) {
            if (pricePlan.getStartDate().isBefore(now)
                    && (pricePlan.getEndDate() == null || pricePlan.getEndDate().isAfter(now))) {
                return pricePlan;
            }
        }
        return null;
    }

    @Override
    public Page<CartDetailResponse> getAllProductFromCarts(PageRequest pageRequest) {
        User user = authUtil.getUser();
        Cart cart =
                cartRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        var cartDetails = cartDetailService
                .findAllByCart(cart, pageRequest)
                .map(cartMapper::toCartDetailResponse);
        cartDetails.forEach(cartDetailResponse -> {
            Variant variant = variantService.findVariantById(cartDetailResponse.getVariant().getId());
            PricePlan pricePlan = getCurrentPricePlan(variant.getId());
            cartDetailResponse.getVariant().setCurrentPricePlan(pricePlanMapper.toPricePlanResponse(pricePlan));
        });
        return cartDetails;
    }

    @Transactional
    @Override
    public void deleteProductFromCart(DeleteManyProductCart cartDetailId) {
        for (Integer id : cartDetailId.getCartDetailIds()) {
            if (!cartDetailService.existsById(id)) {
                throw new AppException(ErrorCode.CART_DETAIL_NOT_FOUND);
            }
            CartDetail cartDetail = cartDetailService.findCartDetailById(id);
            cartDetailService.deleteCartDetail(cartDetail);
        }
    }

    @Override
    public Cart findByUserId(Integer userId) {
        return cartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
