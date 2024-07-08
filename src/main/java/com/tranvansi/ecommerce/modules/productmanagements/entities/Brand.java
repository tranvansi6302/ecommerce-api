package com.tranvansi.ecommerce.modules.productmanagements.entities;

import com.tranvansi.ecommerce.components.enums.BrandStatus;
import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private BrandStatus status;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
