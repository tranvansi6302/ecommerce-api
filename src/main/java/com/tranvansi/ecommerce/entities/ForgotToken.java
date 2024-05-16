package com.tranvansi.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "forgot_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ForgotToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;
    @Column(nullable = false)
    private String email;
}
