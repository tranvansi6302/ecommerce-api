package com.tranvansi.ecommerce.services.products;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    ProductResponse createProduct(CreateProductRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteSoftProduct(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    void restoreProduct(Integer id);
}
