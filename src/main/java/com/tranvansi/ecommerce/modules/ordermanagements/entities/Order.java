package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.tranvansi.ecommerce.components.enums.PaidStatus;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.components.enums.OrderStatus;
import com.tranvansi.ecommerce.components.enums.PaymentMethodType;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String orderCode;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String note;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime canceledDate;

    private String canceledReason;

    private LocalDateTime pendingDate;

    private LocalDateTime confirmedDate;

    private LocalDateTime deliveringDate;

    private LocalDateTime deliveredDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethod;

    private LocalDateTime paidDate;

    private PaidStatus onlinePaymentStatus;

    private Double discountShipping;

    private Double discountOrder;

    private Double shippingFee;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
