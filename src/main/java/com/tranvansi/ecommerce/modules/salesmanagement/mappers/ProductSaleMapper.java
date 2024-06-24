package com.tranvansi.ecommerce.modules.salesmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import com.tranvansi.ecommerce.modules.salesmanagement.responses.ProductSalesResponse;

@Mapper(componentModel = "spring")
public interface ProductSaleMapper {

    @Mapping(target = "id", source = "variant.id")
    @Mapping(target = "variantName", source = "variant.variantName")
    @Mapping(target = "sku", source = "variant.sku")
    @Mapping(target = "color", source = "variant.color")
    @Mapping(target = "size", source = "variant.size")
    ProductSalesResponse.VariantDetail toVariantDetail(ProductSale productSale);
}
