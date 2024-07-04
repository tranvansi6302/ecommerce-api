package com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tranvansi.ecommerce.modules.salesmanagement.responses.ProductSalesResponse;
import org.springframework.data.jpa.domain.Specification;

public interface IProductSaleService {
    Page<ProductSalesResponse> getAllProductSales(PageRequest pageRequest, Specification<ProductSale> specification);
    ProductSalesResponse getProductSaleByProductId(Integer productId);
}
