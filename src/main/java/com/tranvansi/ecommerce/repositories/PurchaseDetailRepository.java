package com.tranvansi.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.PurchaseDetail;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
    boolean existsByPurchaseOrderIdAndVariantId(Integer purchaseOrderId, Integer variantId);
}
