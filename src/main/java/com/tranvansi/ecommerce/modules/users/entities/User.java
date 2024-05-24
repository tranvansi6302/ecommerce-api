package com.tranvansi.ecommerce.modules.users.entities;

import java.util.List;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.common.enums.UserStatus;
import com.tranvansi.ecommerce.modules.addresses.entities.Address;
import com.tranvansi.ecommerce.modules.roles.entities.Role;

import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    private String avatar;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String dateOfBirth;

    private String phoneNumber;

    @Column(columnDefinition = "TINYINT")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @ManyToMany private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
}
