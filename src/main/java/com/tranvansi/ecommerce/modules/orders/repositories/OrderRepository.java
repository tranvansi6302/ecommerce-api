package com.tranvansi.ecommerce.modules.orders.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.orders.entities.Order;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllByUserId(
            Integer userId, Specification<Order> specification, Pageable pageable);

    Optional<Order> findByIdAndUserId(Integer id, Integer userId);
}
