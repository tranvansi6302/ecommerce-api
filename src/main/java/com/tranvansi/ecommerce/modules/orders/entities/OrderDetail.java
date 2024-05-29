package com.tranvansi.ecommerce.modules.orders.entities;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.products.entities.Variant;

import lombok.*;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
