package com.tranvansi.ecommerce.modules.products.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse createProduct(CreateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse updateProduct(Integer id, UpdateProductRequest request);

    Page<ProductDetailResponse> getAllProducts(
            PageRequest pageRequest, Specification<Product> specification);

    ProductDetailResponse getProductById(Integer id);
}
