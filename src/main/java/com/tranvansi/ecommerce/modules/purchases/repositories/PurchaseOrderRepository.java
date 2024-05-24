package com.tranvansi.ecommerce.modules.purchases.repositories;

import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    boolean existsByPurchaseOrderCode(String purchaseOrderCode);
}