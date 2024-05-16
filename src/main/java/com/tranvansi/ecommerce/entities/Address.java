package com.tranvansi.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer isDefault;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
