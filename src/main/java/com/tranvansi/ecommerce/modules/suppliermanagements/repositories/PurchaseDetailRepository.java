package com.tranvansi.ecommerce.modules.suppliermanagements.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseDetail;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
    Optional<PurchaseDetail> findByPurchaseOrderAndVariantId(
            PurchaseOrder purchaseOrder, Integer variantId);
}
