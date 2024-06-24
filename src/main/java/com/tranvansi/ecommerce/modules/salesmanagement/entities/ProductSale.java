package com.tranvansi.ecommerce.modules.salesmanagement.entities;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;

import lombok.*;

@Entity
@Table(name = "product_sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
