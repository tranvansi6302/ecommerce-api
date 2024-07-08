package com.tranvansi.ecommerce.modules.reviewmanagements.entities;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranvansi.ecommerce.components.entities.BaseEntity;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "review")
    @JsonManagedReference
    private List<ReviewImage> reviewImages;
}
