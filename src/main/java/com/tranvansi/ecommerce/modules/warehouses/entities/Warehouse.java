package com.tranvansi.ecommerce.modules.warehouses.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.products.entities.Variant;

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

    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}