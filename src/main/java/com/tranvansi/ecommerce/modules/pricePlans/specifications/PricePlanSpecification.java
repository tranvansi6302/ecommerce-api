package com.tranvansi.ecommerce.modules.pricePlans.specifications;

import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.filters.PricePlanFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PricePlanSpecification implements Specification<PricePlan> {
    private final PricePlanFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<PricePlan> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getVariantName() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").get("variantName"),
                            "%" + filter.getVariantName() + "%"
                    )
            );
        }

        if(filter.getSku() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").get("sku"),
                            "%" + filter.getSku() + "%"
                    )
            );
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
