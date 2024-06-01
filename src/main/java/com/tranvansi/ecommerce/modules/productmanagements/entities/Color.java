package com.tranvansi.ecommerce.modules.productmanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.common.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "colors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Color extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String hex;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
