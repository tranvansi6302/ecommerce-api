package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

import lombok.*;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
