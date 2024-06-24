package com.tranvansi.ecommerce.modules.salesmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Integer> {
    Optional<ProductSale> findByProductId(Integer productId);

    boolean existsByProductIdAndVariantId(Integer productId, Integer variantId);
}
