package com.tranvansi.ecommerce.modules.usermanagements.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default private Integer isDefault = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
