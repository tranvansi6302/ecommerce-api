package com.tranvansi.ecommerce.modules.salesmanagement.specifications;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import com.tranvansi.ecommerce.modules.salesmanagement.filters.ProductSaleFilter;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Warehouse;
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
public class ProductSaleSpecification implements Specification<ProductSale> {
    private final ProductSaleFilter filter;

    @Override
    public Predicate toPredicate( @NonNull Root<ProductSale> root,
                                  @NonNull CriteriaQuery<?> query,
                                  @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(filter.getCategorySlug() != null && !filter.getCategorySlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("product").join("category").get("slug"),
                            filter.getCategorySlug()
                    )
            );
        }
        if(filter.getBrandSlug() != null && !filter.getBrandSlug().isEmpty()) {
            predicates.add(
                    cb.equal(
                            root.join("product").join("brand").get("slug"),
                            filter.getBrandSlug()
                    )
            );
        }

        if(filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            predicates.add(
                    cb.like(
                            root.join("product").get("name"), "%" + filter.getSearch() + "%"
                    )
            );
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
