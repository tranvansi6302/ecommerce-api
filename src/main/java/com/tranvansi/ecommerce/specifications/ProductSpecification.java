package com.tranvansi.ecommerce.specifications;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.entities.Product;
import com.tranvansi.ecommerce.enums.ProductStatus;
import com.tranvansi.ecommerce.filters.ProductFilter;

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
        Predicate predicate = cb.equal(root.get("isDeleted"), ProductStatus.NOT_DELETED.getValue());

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
