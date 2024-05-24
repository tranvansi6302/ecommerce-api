package com.tranvansi.ecommerce.modules.purchases.entities;

import com.tranvansi.ecommerce.modules.products.entities.Variant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sku;

    private Integer quantity;

    private Double purchasePrice;

    private Integer quantityReceived;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

}
