package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.filters.VariantFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VariantSpecification implements Specification<Variant> {
    private final VariantFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Variant> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            Predicate skuPredicate = cb.like(root.get("sku"), "%" + filter.getSearch() + "%");
            Predicate variantNamePredicate =
                    cb.like(root.get("variantName"), "%" + filter.getSearch() + "%");
            Predicate productNamePredicate =
                    cb.like(root.get("productName"), "%" + filter.getSearch() + "%");
            predicates.add(cb.or(skuPredicate, variantNamePredicate, productNamePredicate));
        }
        if (filter.getCategorySlug() != null && !filter.getCategorySlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("product").join("category").get("slug"),
                            filter.getCategorySlug()));
        }
        if (filter.getBrandSlug() != null && !filter.getBrandSlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("product").join("brand").get("slug"), filter.getBrandSlug()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
