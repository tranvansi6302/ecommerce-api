package com.tranvansi.ecommerce.services.products;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.requests.products.UpdateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    ProductResponse createProduct(CreateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteSoftProduct(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    void restoreProduct(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    ProductResponse updateProduct(Integer id, UpdateProductRequest request);
}
