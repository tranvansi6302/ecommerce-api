package com.tranvansi.ecommerce.modules.productmanagements.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.components.enums.PricePlanStatus;

import lombok.*;

@Entity
@Table(name = "price_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricePlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double salePrice;

    private Double promotionPrice;

    private Double discount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private PricePlanStatus status;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @JsonBackReference
    private Variant variant;
}
