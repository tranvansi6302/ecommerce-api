package com.tranvansi.ecommerce.modules.orders.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.orders.entities.Order;
import com.tranvansi.ecommerce.modules.orders.filters.OrderFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderSpecification implements Specification<Order> {
    private final OrderFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Order> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getStatus()));
        }
        if (filter.getSearch() != null) {
            predicates.add(cb.like(root.get("orderCode"), "%" + filter.getSearch() + "%"));
        }

        query.distinct(true);
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
