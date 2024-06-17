package com.tranvansi.ecommerce.modules.productmanagements.entities;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranvansi.ecommerce.components.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "variants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String variantName;

    private String productName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(nullable = false, unique = true)
    private String sku;

    private String color;

    private String size;

    @OneToMany(mappedBy = "variant")
    @JsonManagedReference
    private List<PricePlan> pricePlans;

    @OneToOne(mappedBy = "variant")
    @JsonBackReference
    private Warehouse warehouse;
}
