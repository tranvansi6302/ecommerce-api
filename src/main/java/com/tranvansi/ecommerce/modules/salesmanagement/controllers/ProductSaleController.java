package com.tranvansi.ecommerce.modules.salesmanagement.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ProductSalesResponse> productSalesResponses =
                productSaleService.getAllProductSales(pageRequest);
        PagedResponse<List<ProductSalesResponse>> response =
                BuildResponse.buildPagedResponse(productSalesResponses, pageRequest);
        return ResponseEntity.ok(response);
    }
}
