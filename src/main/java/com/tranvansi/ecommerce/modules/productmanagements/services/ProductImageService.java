package com.tranvansi.ecommerce.modules.productmanagements.services;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ProductImageRepository;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IProductImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductImageRepository productImageRepository;

    @Override
    public void saveProductImage(ProductImage productImage) {
        productImageRepository.save(productImage);
    }

    @Override
    public Integer countProductImageByProductId(Integer productId) {
        return productImageRepository.countByProductId(productId);
    }
}
