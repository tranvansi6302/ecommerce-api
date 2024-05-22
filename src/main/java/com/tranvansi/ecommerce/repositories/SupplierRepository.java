package com.tranvansi.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {}
