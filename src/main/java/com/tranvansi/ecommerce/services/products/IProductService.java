package com.tranvansi.ecommerce.services.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.requests.products.UpdateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.CreateProductResponse;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import com.tranvansi.ecommerce.entities.Product;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse createProduct(CreateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteSoftProduct(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    void restoreProduct(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    CreateProductResponse updateProduct(Integer id, UpdateProductRequest request);

    Page<ProductResponse> getAllProducts(
            PageRequest pageRequest, Specification<Product> specification);
}
