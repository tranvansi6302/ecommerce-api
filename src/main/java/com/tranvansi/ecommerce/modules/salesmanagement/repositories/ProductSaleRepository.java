package com.tranvansi.ecommerce.modules.salesmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;

public interface ProductSaleRepository
        extends JpaRepository<ProductSale, Integer>, JpaSpecificationExecutor<ProductSale> {
    boolean existsByProductIdAndVariantId(Integer productId, Integer variantId);

    List<ProductSale> findAllByProduct(Product product);
}
