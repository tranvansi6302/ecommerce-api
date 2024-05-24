package com.tranvansi.ecommerce.modules.products.specifications;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.filters.ProductFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private final ProductFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Product> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (filter.getCategory() != null) {
            predicate =
                    cb.and(
                            predicate,
                            cb.equal(root.get("category").get("id"), filter.getCategory()));
        }
        if (filter.getBrand() != null) {
            predicate = cb.and(predicate, cb.equal(root.get("brand").get("id"), filter.getBrand()));
        }

        return predicate;
    }
}
