package com.tranvansi.ecommerce.modules.suppliermanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private String sku;

    private Integer quantity;

    private Double purchasePrice;

    private Integer quantityReceived;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
