package com.tranvansi.ecommerce.modules.productmanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.tranvansi.ecommerce.modules.productmanagements.entities.Product;
import com.tranvansi.ecommerce.modules.productmanagements.entities.ProductImage;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.CreateAndUpdateProductResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductImageResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductResponse;
import com.tranvansi.ecommerce.modules.reviewmanagements.repositories.ReviewRepository;
import com.tranvansi.ecommerce.modules.salesmanagement.repositories.SaleRepository;

@Mapper(
        componentModel = "spring",
        uses = {VariantMapper.class, BrandMapper.class, CategoryMapper.class})
public abstract class ProductMapper {

    @Autowired private ReviewRepository reviewRepository;

    @Autowired private SaleRepository saleRepository;

    @Mapping(target = "averageRating", expression = "java(getAverageRating(product))")
    @Mapping(target = "sold", expression = "java(getTotalSoldProduct(product))")
    public abstract ProductDetailResponse toProductDetailResponse(Product product);

    public abstract CreateAndUpdateProductResponse toCreateProductResponse(Product product);

    public abstract ProductResponse toProductResponse(Product product);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    public abstract void updateProduct(
            @MappingTarget Product product, UpdateProductRequest request);

    public abstract Product createProduct(CreateProductRequest request);

    public abstract ProductImageResponse toProductImageResponses(ProductImage productImage);

    public Double getAverageRating(Product product) {
        Integer totalStars = reviewRepository.findTotalStarsByProductId(product.getId());
        Integer reviewCount = reviewRepository.findReviewCountByProductId(product.getId());
        if (totalStars == null || reviewCount == null || reviewCount == 0) {
            return 0.0;
        }
        return totalStars.doubleValue() / reviewCount;
    }

    public Integer getTotalSoldProduct(Product product) {
        Integer totalSold = saleRepository.findTotalSoldByProductId(product.getId());
        if (totalSold == null) {
            return 0;
        }
        return totalSold;
    }
}
