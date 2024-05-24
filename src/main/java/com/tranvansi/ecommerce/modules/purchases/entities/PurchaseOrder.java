package com.tranvansi.ecommerce.modules.purchases.entities;

import com.tranvansi.ecommerce.common.entities.BaseEntity;
import com.tranvansi.ecommerce.common.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.users.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
