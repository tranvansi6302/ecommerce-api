package com.tranvansi.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/inventories")
@RequiredArgsConstructor
public class InventoryController {
    //    private final IInventoryService inventoryService;
    //    @PostMapping("")
    //    public ResponseEntity<ApiResponse<?>> createInventory(@RequestBody CreateInventoryRequest
    // request) {
    //        inventoryService.createInventory(request);
    //        return ResponseEntity.ok(ApiResponse.builder().message("Inventory created
    // successfully").build());
    //    }
}
