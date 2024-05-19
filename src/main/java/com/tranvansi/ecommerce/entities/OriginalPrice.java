package com.tranvansi.ecommerce.entities;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "original_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OriginalPrice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Variant variant;
}
