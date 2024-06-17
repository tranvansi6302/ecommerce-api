package com.tranvansi.ecommerce.modules.suppliermanagements.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.*;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String purchaseOrderCode;

    private LocalDateTime purchaseOrderDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(columnDefinition = "TEXT")
    private String cancelReason;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseDetail> purchaseDetails;
}
