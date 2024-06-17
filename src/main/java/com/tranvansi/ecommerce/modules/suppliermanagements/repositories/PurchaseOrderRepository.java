package com.tranvansi.ecommerce.modules.suppliermanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>,
        JpaSpecificationExecutor<PurchaseOrder> {
    boolean existsByPurchaseOrderCode(String purchaseOrderCode);
}
