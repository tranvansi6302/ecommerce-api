package com.tranvansi.ecommerce.modules.purchases.repositories;

import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
}