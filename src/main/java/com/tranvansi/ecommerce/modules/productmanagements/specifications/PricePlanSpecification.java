package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.filters.PricePlanFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PricePlanSpecification implements Specification<PricePlan> {
    private final PricePlanFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<PricePlan> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getVariantName() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").get("variantName"),
                            "%" + filter.getVariantName() + "%"));
        }

        if (filter.getSku() != null) {
            predicates.add(cb.like(root.join("variant").get("sku"), "%" + filter.getSku() + "%"));
        }

        if (filter.getCategorySlug() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").join("product").join("category").get("slug"),
                            "%" + filter.getCategorySlug() + "%"));
        }

        if (filter.getBrandSlug() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").join("product").join("brand").get("slug"),
                            "%" + filter.getBrandSlug() + "%"));
        }

        query.distinct(true);

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
