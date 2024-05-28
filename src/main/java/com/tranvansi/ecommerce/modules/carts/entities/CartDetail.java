package com.tranvansi.ecommerce.modules.carts.entities;

import com.tranvansi.ecommerce.modules.products.entities.Variant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail {
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
