package com.tranvansi.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity{
    @Id
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;
}
