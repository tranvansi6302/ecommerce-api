package com.tranvansi.ecommerce.entities;

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

    @Builder.Default private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Variant variant;
}
