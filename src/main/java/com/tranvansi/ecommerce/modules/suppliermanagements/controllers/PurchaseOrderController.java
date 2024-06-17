package com.tranvansi.ecommerce.modules.suppliermanagements.controllers;

import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.productmanagements.filters.VariantFilter;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.specifications.VariantSpecification;
import com.tranvansi.ecommerce.modules.suppliermanagements.filters.PurchaseOrdersFilter;
import com.tranvansi.ecommerce.modules.suppliermanagements.specifications.PurchaseOrdersSpecification;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IPurchaseOrderService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final IPurchaseOrderService purchaseOrderService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<PurchaseOrderResponse>>> getAllPurchaseOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "status", required = false) PurchaseOrderStatus status,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "supplier", required = false) Integer supplierId
          ) {
        PurchaseOrdersFilter filter =
                PurchaseOrdersFilter
                        .builder()
                        .status(status)
                        .search(search)
                        .supplierId(supplierId)
                        .build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<PurchaseOrderResponse> purchaseOrderResponses =
                purchaseOrderService.getAllPurchaseOrders(pageRequest,
                        new PurchaseOrdersSpecification(filter));
        PagedResponse<List<PurchaseOrderResponse>> response =
                BuildResponse.buildPagedResponse(purchaseOrderResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> getPurchaseOrderById(
            @PathVariable Integer id) {
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.getPurchaseOrderById(id);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchaseOrder(
            @RequestBody @Valid CreatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse =
                purchaseOrderService.createPurchaseOrder(request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_CREATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updatePurchaseOrder(
            @PathVariable Integer id, @RequestBody @Valid UpdatePurchaseOrderRequest request) {
        PurchaseOrderResponse purchaseOrderResponse =
                purchaseOrderService.updatePurchaseOrder(id, request);
        ApiResponse<PurchaseOrderResponse> response =
                ApiResponse.<PurchaseOrderResponse>builder()
                        .result(purchaseOrderResponse)
                        .message(Message.PURCHASE_ORDER_UPDATED_SUCCESSFULLY.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
