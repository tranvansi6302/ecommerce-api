package com.tranvansi.ecommerce.modules.pricePlans.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.common.enums.PricePlanStatus;
import com.tranvansi.ecommerce.modules.products.entities.Variant;

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
