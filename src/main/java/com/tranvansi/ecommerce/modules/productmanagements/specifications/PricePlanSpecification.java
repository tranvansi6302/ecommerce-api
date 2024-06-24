package com.tranvansi.ecommerce.modules.productmanagements.specifications;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.filters.PricePlanFilter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PricePlanSpecification implements Specification<PricePlan> {
    private final PricePlanFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<PricePlan> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {

            Join<Product, Variant> variantJoin = root.join("variant", JoinType.INNER);

            Predicate variantNamePredicate =
                    cb.like(variantJoin.get("variantName"), "%" + filter.getSearch() + "%");
            Predicate skuPredicate =
                    cb.like(variantJoin.get("sku"), "%" + filter.getSearch() + "%");
            Predicate productNamePredicate =
                    cb.like(variantJoin.get("productName"), "%" + filter.getSearch() + "%");

            predicates.add(cb.or(variantNamePredicate, skuPredicate, productNamePredicate));
        }

        if (filter.getCategorySlug() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").join("product").join("category").get("slug"),
                            "%" + filter.getCategorySlug() + "%"));
        }

        if (filter.getBrandSlug() != null) {
            predicates.add(
                    cb.like(
                            root.join("variant").join("product").join("brand").get("slug"),
                            "%" + filter.getBrandSlug() + "%"));
        }

        query.distinct(true);

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
