package com.tranvansi.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {}
