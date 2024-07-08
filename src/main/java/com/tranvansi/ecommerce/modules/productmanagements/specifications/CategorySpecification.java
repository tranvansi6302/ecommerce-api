package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Category;
import com.tranvansi.ecommerce.modules.productmanagements.filters.CategoryFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategorySpecification implements Specification<Category> {
    private final CategoryFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Category> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + filter.getSearch() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
