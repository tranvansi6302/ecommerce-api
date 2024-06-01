package com.tranvansi.ecommerce.modules.reviews.specifications;

import com.tranvansi.ecommerce.modules.reviews.entities.Review;
import com.tranvansi.ecommerce.modules.reviews.filters.ReviewFilter;
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
public class ReviewSpecification implements Specification<Review> {
    private final ReviewFilter filter;
    @Override
    public Predicate toPredicate(
            @NonNull Root<Review> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getRating()!=null) {
            predicates.add(cb.equal(root.get("rating"), filter.getRating()));
        }
        if(filter.getProductName()!=null) {
            predicates.add(cb.like(root.join("product").get("name"), "%" + filter.getProductName() + "%"));
        }

        query.distinct(true);

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
