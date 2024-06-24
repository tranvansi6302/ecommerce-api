package com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tranvansi.ecommerce.modules.salesmanagement.responses.ProductSalesResponse;

public interface IProductSaleService {
    Page<ProductSalesResponse> getAllProductSales(PageRequest pageRequest);
}
