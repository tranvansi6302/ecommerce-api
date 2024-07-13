package com.tranvansi.ecommerce.modules.ordermanagements.repositories;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findAllByOrderId(Integer orderId);
}
