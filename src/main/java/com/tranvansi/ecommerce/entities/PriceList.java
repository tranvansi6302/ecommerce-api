package com.tranvansi.ecommerce.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "price_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double salePrice;

    private String sku;

    private LocalDateTime effectiveDate;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
