package com.tranvansi.ecommerce.modules.suppliermanagements.specifications;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliermanagements.filters.PurchaseOrdersFilter;
import com.tranvansi.ecommerce.modules.suppliermanagements.filters.SupplierFilter;
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
public class SuppliersSpecification implements Specification<Supplier> {
    private final SupplierFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Supplier> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getStatus()));
        }

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {

            Predicate namePredicate = cb.like(root.get("name"), "%" + filter.getSearch() + "%");
            Predicate taxCodePredicate = cb.like(root.get("taxCode"), "%" + filter.getSearch() + "%");
            predicates.add(cb.or(namePredicate, taxCodePredicate));

        }



        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
