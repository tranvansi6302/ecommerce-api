package com.tranvansi.ecommerce.modules.suppliermanagements.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.filters.WarehouseFilter;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.WarehouseResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IWarehouseService;
import com.tranvansi.ecommerce.modules.suppliermanagements.specifications.WarehouseSpecification;

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
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "last-updated", required = false) String lastUpdatedSort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "category", required = false) String categorySlug,
            @RequestParam(name = "brand", required = false) String brandSlug,
            @RequestParam(name = "sku", required = false) String sku) {
        WarehouseFilter filter =
                WarehouseFilter.builder()
                        .search(search)
                        .categorySlug(categorySlug)
                        .brandSlug(brandSlug)
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

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseResponse>> getWarehouseById(
            @PathVariable Integer id) {
        WarehouseResponse warehouseResponse = warehouseService.getWarehouseById(id);
        ApiResponse<WarehouseResponse> response =
                ApiResponse.<WarehouseResponse>builder().result(warehouseResponse).build();
        return ResponseEntity.ok(response);
    }
}
