package com.tranvansi.ecommerce.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "promotion_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double price;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(columnDefinition = "TINYINT")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Variant variant;
}
