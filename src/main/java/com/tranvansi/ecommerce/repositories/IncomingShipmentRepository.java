package com.tranvansi.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.IncomingShipment;

@Repository
public interface IncomingShipmentRepository extends JpaRepository<IncomingShipment, Integer> {}
