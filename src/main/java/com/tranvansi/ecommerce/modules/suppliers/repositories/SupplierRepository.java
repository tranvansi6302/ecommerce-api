package com.tranvansi.ecommerce.modules.suppliers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsByName(String name);

    boolean existsByTaxCode(String taxCode);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
