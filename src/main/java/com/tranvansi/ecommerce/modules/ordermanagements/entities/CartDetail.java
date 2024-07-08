package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
