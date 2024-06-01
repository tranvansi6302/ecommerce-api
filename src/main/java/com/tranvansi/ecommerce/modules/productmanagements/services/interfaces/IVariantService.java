package com.tranvansi.ecommerce.modules.productmanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;

public interface IVariantService {
    Variant findVariantById(Integer id);

    boolean existsBySku(String sku);

    Variant saveVariant(Variant variant);
}
