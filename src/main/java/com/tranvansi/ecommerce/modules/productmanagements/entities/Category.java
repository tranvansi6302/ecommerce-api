package com.tranvansi.ecommerce.modules.productmanagements.entities;

import com.tranvansi.ecommerce.components.enums.CategoryStatus;
import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Enumerated(EnumType.ORDINAL)
    private CategoryStatus status;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
