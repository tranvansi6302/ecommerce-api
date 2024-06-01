package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.common.enums.OrderStatus;
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

    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
