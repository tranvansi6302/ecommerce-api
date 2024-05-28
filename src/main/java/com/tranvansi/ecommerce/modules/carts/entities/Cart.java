package com.tranvansi.ecommerce.modules.carts.entities;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.users.entities.User;
import jakarta.persistence.*;
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
