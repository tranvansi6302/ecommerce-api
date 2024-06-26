package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
