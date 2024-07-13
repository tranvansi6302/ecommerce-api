package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import java.util.List;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteProductImageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IProductImageService {
    void saveProductImage(ProductImage productImage);

    Integer countProductImageByProductId(Integer productId);

    List<ProductImage> findByProductId(Integer productId);

    void deleteProductImage(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteManyProductImages(DeleteProductImageRequest request);
}
