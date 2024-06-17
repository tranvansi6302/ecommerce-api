package com.tranvansi.ecommerce.modules.suppliermanagements.specifications;

import com.tranvansi.ecommerce.modules.productmanagements.filters.VariantFilter;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.suppliermanagements.filters.PurchaseOrdersFilter;
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
public class PurchaseOrdersSpecification implements Specification<PurchaseOrder> {
    private final PurchaseOrdersFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<PurchaseOrder> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(filter.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getStatus()));
        }

        if(filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            predicates.add(cb.like(root.get("purchaseOrderCode"), "%" + filter.getSearch() + "%"));
        }

        if(filter.getSupplierId()!=null) {
            predicates.add(
                    cb.equal(root.join("supplier").get("id"), filter.getSupplierId())
            );
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
