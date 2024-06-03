package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UploadProductImagesRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductResponse;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse createProduct(CreateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse updateProduct(Integer id, UpdateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    ProductResponse uploadImageProducts(Integer id, UploadProductImagesRequest request);

    Page<ProductDetailResponse> getAllProducts(
            PageRequest pageRequest, Specification<Product> specification);

    ProductDetailResponse getProductById(Integer id);

    Product findProductById(Integer id);
}
