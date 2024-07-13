package com.tranvansi.ecommerce.components.scheduleds;

import com.tranvansi.ecommerce.components.enums.PaidStatus;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.OrderDetail;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.OrderDetailRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCleanupScheduler {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Scheduled(fixedRate = 3600000) // 1h
    public void cleanupUnpaidOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24); // 24h
        Order order = orderRepository.findByPendingDateBeforeAndOnlinePaymentStatus(cutoff, PaidStatus.UNPAID).orElse(null);
        if (order != null) {
            var orderDetails = orderDetailRepository.findAllByOrderId(order.getId());
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailRepository.delete(orderDetail);
            }
            orderRepository.delete(order);
        }
        log.info("Clear order unpaid");
    }
}
