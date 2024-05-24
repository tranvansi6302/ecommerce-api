package com.tranvansi.ecommerce.modules.suppliers.entities;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.common.enums.SupplierStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String taxCode;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    private SupplierStatus status;

}
