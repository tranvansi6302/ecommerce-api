package com.tranvansi.ecommerce.modules.ordermanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import com.tranvansi.ecommerce.components.enums.OrderStatus;
import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import com.tranvansi.ecommerce.modules.ordermanagements.filters.OrderFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderSpecification
        implements Specification<com.tranvansi.ecommerce.modules.ordermanagements.entities.Order> {
    private final OrderFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Order> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatus() != null) {
            if (filter.getStatus().equals(OrderStatus.PENDING)) {
                Predicate confirmed = cb.equal(root.get("status"), OrderStatus.PENDING);
                Predicate paid = cb.equal(root.get("status"), OrderStatus.PAID);
                Predicate unPaid = cb.equal(root.get("status"), OrderStatus.UNPAID);
                predicates.add(cb.or(confirmed, paid));
            } else {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
        }
        if (filter.getSearch() != null) {
            predicates.add(cb.like(root.get("orderCode"), "%" + filter.getSearch() + "%"));
        }

        query.distinct(true);
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
