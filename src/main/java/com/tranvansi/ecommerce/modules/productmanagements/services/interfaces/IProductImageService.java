package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;

import java.util.List;

public interface IProductImageService {
    void saveProductImage(ProductImage productImage);

    Integer countProductImageByProductId(Integer productId);

    List<ProductImage> findByProductId(Integer productId);

    void deleteProductImage(Integer id);
}
