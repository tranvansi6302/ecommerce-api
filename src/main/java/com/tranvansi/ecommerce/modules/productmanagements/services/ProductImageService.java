package com.tranvansi.ecommerce.modules.productmanagements.services;

import java.util.List;

import com.tranvansi.ecommerce.components.services.AmazonClientService;
import com.tranvansi.ecommerce.modules.productmanagements.requests.DeleteProductImageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ProductImageRepository;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IProductImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductImageRepository productImageRepository;
    private final AmazonClientService amazonClientService;
    @Override
    public void saveProductImage(ProductImage productImage) {
        productImageRepository.save(productImage);
    }

    @Override
    public Integer countProductImageByProductId(Integer productId) {
        return productImageRepository.countByProductId(productId);
    }

    @Override
    public List<ProductImage> findByProductId(Integer productId) {
        return productImageRepository.findByProductId(productId);
    }

    @Override
    public void deleteProductImage(Integer id) {
        productImageRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteManyProductImages(DeleteProductImageRequest request) {

        var productImages = findByProductId(request.getProductId());
        for (ProductImage image : productImages) {
            amazonClientService.deleteFileFromS3Bucket(image.getUrl());
            deleteProductImage(image.getId());
        }
    }
}
