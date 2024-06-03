package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;

public interface IProductImageService {
    void saveProductImage(ProductImage productImage);

    Integer countProductImageByProductId(Integer productId);
}
