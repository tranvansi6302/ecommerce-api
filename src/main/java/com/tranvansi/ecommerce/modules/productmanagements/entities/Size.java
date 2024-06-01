package com.tranvansi.ecommerce.modules.productmanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.common.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
