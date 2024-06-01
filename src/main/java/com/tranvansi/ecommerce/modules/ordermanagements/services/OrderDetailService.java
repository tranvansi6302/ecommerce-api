package com.tranvansi.ecommerce.modules.ordermanagements.services;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.ordermanagements.repositories.OrderDetailRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IOrderDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetail(
            com.tranvansi.ecommerce.modules.ordermanagements.entities.OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
}
