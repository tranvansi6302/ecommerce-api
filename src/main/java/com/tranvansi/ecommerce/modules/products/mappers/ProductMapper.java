package com.tranvansi.ecommerce.modules.products.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.tranvansi.ecommerce.modules.brands.mappers.BrandMapper;
import com.tranvansi.ecommerce.modules.categories.mappers.CategoryMapper;
import com.tranvansi.ecommerce.modules.products.entities.Product;
import com.tranvansi.ecommerce.modules.products.requests.CreateProductRequest;
import com.tranvansi.ecommerce.modules.products.requests.UpdateProductRequest;
import com.tranvansi.ecommerce.modules.products.responses.CreateProductResponse;
import com.tranvansi.ecommerce.modules.products.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.reviews.repositories.ReviewRepository;

@Mapper(
        componentModel = "spring",
        uses = {VariantMapper.class, BrandMapper.class, CategoryMapper.class})
public abstract class ProductMapper {

    @Autowired
    private ReviewRepository reviewRepository;

    @Mapping(target = "averageRating", expression = "java(getAverageRating(product))")
    public abstract ProductDetailResponse toProductDetailResponse(Product product);

    public abstract CreateProductResponse toProductResponse(Product product);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    public abstract void updateProduct(@MappingTarget Product product, UpdateProductRequest request);

    public abstract Product createProduct(CreateProductRequest request);

    protected Double getAverageRating(Product product) {
        Integer totalStars = reviewRepository.findTotalStarsByProductId(product.getId());
        Integer reviewCount = reviewRepository.findReviewCountByProductId(product.getId());
        if (totalStars == null || reviewCount == null || reviewCount == 0) {
            return 0.0;
        }
        return totalStars.doubleValue() / reviewCount;
    }
}
