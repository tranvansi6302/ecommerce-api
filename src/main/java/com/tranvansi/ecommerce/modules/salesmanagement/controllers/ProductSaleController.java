package com.tranvansi.ecommerce.modules.salesmanagement.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.ProductDetailResponse;
import com.tranvansi.ecommerce.modules.salesmanagement.filters.ProductSaleFilter;
import com.tranvansi.ecommerce.modules.salesmanagement.specifications.ProductSaleSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.salesmanagement.responses.ProductSalesResponse;
import com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces.IProductSaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/products/sales")
@RequiredArgsConstructor
public class ProductSaleController {
    private final IProductSaleService productSaleService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<ProductSalesResponse>>> getAllProductSales(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "rating", required = false) Integer ratingMin,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice) {

        ProductSaleFilter filter = ProductSaleFilter.builder()
                .categorySlug(categorySlug)
                .brandSlug(brandSlug)
                .search(search)
                .build();

        Sort sort = Sort.unsorted();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<ProductSalesResponse> productSalesResponses = productSaleService.getAllProductSales(pageRequest, new ProductSaleSpecification(filter));

        List<ProductSalesResponse> filteredProducts = productSalesResponses.getContent();

        // Lọc theo ratingMin
        if (ratingMin != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getAverageRating() >= ratingMin)
                    .collect(Collectors.toList());
        }

        // Lọc theo khoảng giá
        if (minPrice != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getMinPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getMinPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // Sắp xếp theo tiêu chí sortBy
        Comparator<ProductSalesResponse> comparator = null;
        if (sortBy == null) {
            comparator = Comparator.comparing(ProductSalesResponse::getCreatedAt);
        } else if ("price".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(ProductSalesResponse::getMinPrice);
        } else if ("sold".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(ProductSalesResponse::getTotalSold);
        } else if ("rating".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(ProductSalesResponse::getAverageRating);
        }

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }
            filteredProducts = filteredProducts.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }

        // Tạo lại đối tượng PageImpl với dữ liệu đã lọc và sắp xếp
        productSalesResponses = new PageImpl<>(filteredProducts, pageRequest, productSalesResponses.getTotalElements());

        PagedResponse<List<ProductSalesResponse>> response = BuildResponse.buildPagedResponse(productSalesResponses, pageRequest);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductSalesResponse>> getProductSaleByProductId(@PathVariable Integer productId) {
        ProductSalesResponse productSalesResponse = productSaleService.getProductSaleByProductId(productId);
        ApiResponse<ProductSalesResponse> response =
                ApiResponse.<ProductSalesResponse>builder().result(productSalesResponse).build();
        return ResponseEntity.ok(response);
    }
}
