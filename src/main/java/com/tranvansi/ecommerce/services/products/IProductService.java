package com.tranvansi.ecommerce.services.products;

import com.tranvansi.ecommerce.dtos.requests.products.CreateProductRequest;
import com.tranvansi.ecommerce.dtos.responses.products.ProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IProductService {

    @PreAuthorize("hasRole('ADMIN')")
    ProductResponse createProduct(CreateProductRequest request);
}
