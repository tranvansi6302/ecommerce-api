package com.tranvansi.ecommerce.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String sku;

    private Integer quantity;

    private LocalDateTime lastUpdated;

    private String location;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name = "purchase_detail_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PurchaseDetail purchaseDetail;
}
