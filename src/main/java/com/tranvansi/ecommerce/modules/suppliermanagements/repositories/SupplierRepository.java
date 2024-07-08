package com.tranvansi.ecommerce.modules.suppliermanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Supplier;

@Repository
public interface SupplierRepository
        extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {
    boolean existsByName(String name);

    boolean existsByTaxCode(String taxCode);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
