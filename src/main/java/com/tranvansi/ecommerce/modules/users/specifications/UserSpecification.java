package com.tranvansi.ecommerce.modules.users.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.filters.UserFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {
    private final UserFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<User> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getEmail() != null) {
            predicates.add(cb.like(root.get("email"), "%" + filter.getEmail() + "%"));
        }
        if (filter.getBlocked() != null) {
            predicates.add(cb.equal(root.get("blocked"), filter.getBlocked()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
