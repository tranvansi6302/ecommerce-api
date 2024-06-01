package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.WarehouseFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseSpecification implements Specification<Warehouse> {
    private final WarehouseFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Warehouse> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getVariantName() != null && !filter.getVariantName().isEmpty()) {
            predicates.add(
                    cb.like(
                            root.join("variant").get("variantName"),
                            "%" + filter.getVariantName() + "%"));
        }
        if (filter.getCategorySlug() != null && !filter.getCategorySlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("variant").join("product").join("category").get("slug"),
                            filter.getCategorySlug()));
        }
        if (filter.getBrandSlug() != null && !filter.getBrandSlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("variant").join("product").join("brand").get("slug"),
                            filter.getBrandSlug()));
        }
        if (filter.getSku() != null && !filter.getSku().isEmpty()) {
            predicates.add(cb.like(root.get("sku"), "%" + filter.getSku() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
