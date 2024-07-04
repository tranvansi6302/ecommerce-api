package com.tranvansi.ecommerce.modules.salesmanagement.repositories;

import java.util.List;
import java.util.Optional;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Integer>, JpaSpecificationExecutor<ProductSale> {
    boolean existsByProductIdAndVariantId(Integer productId, Integer variantId);
    List<ProductSale> findAllByProduct(Product product);
}
