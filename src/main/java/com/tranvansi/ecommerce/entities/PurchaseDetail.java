package com.tranvansi.ecommerce.entities;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "purchase_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String sku;

    private Integer quantity;

    private Double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PurchaseOrder purchaseOrder;
}
