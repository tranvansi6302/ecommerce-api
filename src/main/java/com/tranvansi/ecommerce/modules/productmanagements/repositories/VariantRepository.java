package com.tranvansi.ecommerce.modules.productmanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

import java.util.List;

@Repository
public interface VariantRepository
        extends JpaRepository<Variant, Integer>, JpaSpecificationExecutor<Variant> {
    boolean existsBySkuAndIdNot(String sku, Integer id);

    boolean existsByVariantNameAndIdNot(String variantName, Integer id);

    boolean existsByColorAndSizeAndIdNotAndProductId(
            String color, String size, Integer id, Integer productId);

}
