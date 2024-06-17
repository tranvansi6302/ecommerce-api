package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductSalesResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.WarehouseResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
@Mapper(componentModel = "spring", uses = {ProductMapper.class, PricePlanMapper.class, VariantMapper.class})
public abstract class WarehouseMapper {

    @Autowired
    private ProductMapper productMapper;

    @Mapping(target = "variant.pricePlans", source = "variant.pricePlans")
    @Mapping(target = "variant.productImages", source = "variant.product.productImages")
    @Mapping(target = "variant.productId", source = "variant.product.id")
    public abstract WarehouseResponse toWarehouseResponse(Warehouse warehouse);

    @Mapping(target = "product.id", source = "variant.product.id")
    @Mapping(target = "product.name", source = "variant.product.name")
    @Mapping(target = "product.description", source = "variant.product.description")
    @Mapping(target = "product.category", source = "variant.product.category")
    @Mapping(target = "product.brand", source = "variant.product.brand")
    @Mapping(target = "product.productImages", source = "variant.product.productImages")
    @Mapping(target = "product.variants", source = "variant.product.variants")
    @Mapping(target = "product.averageRating", source = "variant.product", qualifiedByName = "calculateAverageRating")
    @Mapping(target = "product.sold", source = "variant.product", qualifiedByName = "calculateTotalSold")

    public abstract ProductSalesResponse toProductSalesResponse(Warehouse warehouse);

    @AfterMapping
    protected void fillProductDetails(@MappingTarget ProductSalesResponse productSalesResponse, Warehouse warehouse) {
        if (productSalesResponse == null) {
            productSalesResponse = new ProductSalesResponse();
        }
        if (productSalesResponse.getProduct() == null) {
            ProductDetailResponse productDetailResponse = productMapper.toProductDetailResponse(warehouse.getVariant().getProduct());
            if (productDetailResponse != null) {
                productSalesResponse.setProduct(productDetailResponse);
            }
        }
    }

    // Custom method to calculate average rating
    @Named("calculateAverageRating")
    protected Double calculateAverageRating(Product product) {
        return productMapper.getAverageRating(product);
    }

    // Custom method to calculate total sold
    @Named("calculateTotalSold")
    protected Integer calculateTotalSold(Product product) {
        return productMapper.getTotalSoldProduct(product);
    }
}
