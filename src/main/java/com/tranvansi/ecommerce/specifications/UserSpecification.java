package com.tranvansi.ecommerce.specifications;

import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.filters.UserFilter;
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
public class UserSpecification implements Specification<User> {
    private final UserFilter filter;
    @Override
    public Predicate toPredicate(@NonNull Root<User> root,
                                 @NonNull CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
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