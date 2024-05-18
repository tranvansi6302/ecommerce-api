package com.tranvansi.ecommerce.services.products;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.entities.Brand;
import com.tranvansi.ecommerce.entities.Category;
import com.tranvansi.ecommerce.entities.Product;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.enums.ProductStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.ProductMapper;
import com.tranvansi.ecommerce.repositories.BrandRepository;
import com.tranvansi.ecommerce.repositories.CategoryRepository;
import com.tranvansi.ecommerce.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        Category category =
                categoryRepository
                        .findById(request.getCategoryId())
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Brand brand =
                brandRepository
                        .findById(request.getBrandId())
                        .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setBrand(brand);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteSoftProduct(Integer id) {
        Product product =
                productRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setIsDeleted(ProductStatus.SOFT_DELETED.getValue());
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Scheduled(cron = "0 0 * * *")
    @Transactional
    public void deleteOldSoftDeletedProducts() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        productRepository.deleteByIsDeletedAndDeletedAtBefore(1, thirtyDaysAgo);
    }

    @Override
    public void restoreProduct(Integer id) {
        Product product =
                productRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setIsDeleted(ProductStatus.NOT_DELETED.getValue());
        product.setDeletedAt(null);
        productRepository.save(product);
    }
}
