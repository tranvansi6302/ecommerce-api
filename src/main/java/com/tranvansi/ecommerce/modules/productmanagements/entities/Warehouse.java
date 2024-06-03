package com.tranvansi.ecommerce.modules.productmanagements.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranvansi.ecommerce.components.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false, unique = true)
    private String sku;

    private Double purchasePrice;

    private LocalDateTime lastUpdated;

    @OneToOne
    @JoinColumn(name = "variant_id")
    @JsonManagedReference
    private Variant variant;
}
