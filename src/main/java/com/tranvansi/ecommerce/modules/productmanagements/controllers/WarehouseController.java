package com.tranvansi.ecommerce.modules.productmanagements.controllers;

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
import com.tranvansi.ecommerce.modules.productmanagements.filters.WarehouseFilter;
import com.tranvansi.ecommerce.modules.productmanagements.responses.WarehouseResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IWarehouseService;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.WarehouseSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final IWarehouseService warehouseService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<WarehouseResponse>>> getAllWarehouses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", required = false) String sortOrder,
            @RequestParam(name = "last-updated", required = false) String lastUpdatedSort,
            @RequestParam(name = "name", required = false) String variantName,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sku", required = false) String sku) {
        WarehouseFilter filter =
                WarehouseFilter.builder()
                        .variantName(variantName)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
                        .sku(sku)
                        .build();

        Sort sort = Sort.unsorted();
        if (lastUpdatedSort != null && !lastUpdatedSort.isEmpty()) {
            sort =
                    Sort.by(
                            lastUpdatedSort.equalsIgnoreCase("asc")
                                    ? Sort.Order.asc("lastUpdated")
                                    : Sort.Order.desc("lastUpdated"));
        } else if (sortOrder != null && !sortOrder.isEmpty()) {
            sort =
                    Sort.by(
                            sortOrder.equalsIgnoreCase("asc")
                                    ? Sort.Order.asc("createdAt")
                                    : Sort.Order.desc("createdAt"));
        }

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<WarehouseResponse> warehouseResponses =
                warehouseService.getAllWarehouses(pageRequest, new WarehouseSpecification(filter));
        PagedResponse<List<WarehouseResponse>> response =
                BuildResponse.buildPagedResponse(warehouseResponses, pageRequest);
        return ResponseEntity.ok(response);
    }
}
