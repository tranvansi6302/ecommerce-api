package com.tranvansi.ecommerce.modules.salesmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.product.id = :productId")
    Integer findTotalSoldByProductId(Integer productId);
}
