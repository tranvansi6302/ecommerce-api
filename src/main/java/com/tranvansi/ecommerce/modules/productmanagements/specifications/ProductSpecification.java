package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.filters.ProductFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private final ProductFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Product> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getProductName() != null && !filter.getProductName().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + filter.getProductName() + "%"));
        }

        if (filter.getCategorySlug() != null && !filter.getCategorySlug().isEmpty()) {
            predicates.add(cb.equal(root.join("category").get("slug"), filter.getCategorySlug()));
        }
        if (filter.getBrandSlug() != null && !filter.getBrandSlug().isEmpty()) {
            predicates.add(cb.equal(root.join("brand").get("slug"), filter.getBrandSlug()));
        }

        if (filter.getPriceMin() != null || filter.getPriceMax() != null) {
            Join<Product, Variant> variantJoin = root.join("variants", JoinType.LEFT);
            Join<Variant, PricePlan> pricePlanJoin = variantJoin.join("pricePlans", JoinType.LEFT);

            if (filter.getPriceMin() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                pricePlanJoin.get("salePrice"), filter.getPriceMin()));
            }
            if (filter.getPriceMax() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(pricePlanJoin.get("salePrice"), filter.getPriceMax()));
            }
        }

        query.distinct(true);

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
