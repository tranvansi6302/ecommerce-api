package com.tranvansi.ecommerce.modules.purchases.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseDetail;
import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseOrder;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
    Optional<PurchaseDetail> findByPurchaseOrderAndVariantId(
            PurchaseOrder purchaseOrder, Integer variantId);
}
