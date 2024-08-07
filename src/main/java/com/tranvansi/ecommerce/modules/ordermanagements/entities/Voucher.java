package com.tranvansi.ecommerce.modules.ordermanagements.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.components.enums.DiscountType;
import com.tranvansi.ecommerce.components.enums.VoucherType;

import lombok.*;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private VoucherType voucherType;

    @Enumerated(EnumType.ORDINAL)
    private DiscountType discountType;

    private Double value;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double minOrderValue;
}
