package com.tranvansi.ecommerce.modules.usermanagements.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;

import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;

    private Integer provinceId;
    private Integer districtId;
    private String wardId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default private Integer isDefault = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
