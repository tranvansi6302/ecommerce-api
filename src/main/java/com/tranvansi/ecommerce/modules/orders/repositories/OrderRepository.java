package com.tranvansi.ecommerce.modules.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.orders.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {}
