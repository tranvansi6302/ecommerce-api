package com.tranvansi.ecommerce.services.purchases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseDetailRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.PurchaseDetailRequest;
import com.tranvansi.ecommerce.dtos.requests.purchases.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.dtos.responses.purchases.CreatePurchaseOrderDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.purchases.PurchaseOrderDetailResponse;
import com.tranvansi.ecommerce.dtos.responses.purchases.PurchaseOrderResponse;
import com.tranvansi.ecommerce.entities.*;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.enums.PurchaseOrderPaymentStatus;
import com.tranvansi.ecommerce.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.PurchaseDetailMapper;
import com.tranvansi.ecommerce.mappers.PurchaseOrderMapper;
import com.tranvansi.ecommerce.repositories.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService implements IPurchaseService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final VariantRepository variantRepository;
    private final InventoryRepository inventoryRepository;
    private final IncomingShipmentRepository incomingShipmentRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final UserRepository userRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseDetailMapper purchaseDetailMapper;

    @Override
    public PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request) {
        Supplier supplier =
                supplierRepository
                        .findById(request.getSupplierId())
                        .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (purchaseOrderRepository.existsByPurchaseOrderCode(request.getPurchaseOrderCode())) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_CODE_EXISTED);
        }
        PurchaseOrder purchaseOrder = purchaseOrderMapper.createPurchaseOrder(request);
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING.getValue());
        purchaseOrder.setUser(user);
        purchaseOrder.setPurchaseOrderDate(LocalDateTime.now());
        purchaseOrder.setPaymentStatus(PurchaseOrderPaymentStatus.PENDING.getValue());
        return purchaseOrderMapper.toPurchaseOrderResponse(
                purchaseOrderRepository.save(purchaseOrder));
    }

    @Override
    public CreatePurchaseOrderDetailResponse createPurchaseDetail(
            CreatePurchaseDetailRequest request) {
        List<PurchaseOrderDetailResponse> purchaseOrderDetailResponses = new ArrayList<>();
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository
                        .findById(request.getPurchaseOrderId())
                        .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_NOT_FOUND));
        Integer purchaseOrderId = request.getPurchaseOrderId();
        for (PurchaseDetailRequest purchaseDetailRequest : request.getPurchaseDetails()) {

            if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.COMPLETED.getValue())) {
                throw new AppException(ErrorCode.PURCHASE_ORDER_COMPLETED);
            }

            if (purchaseDetailRepository.existsByPurchaseOrderIdAndVariantId(
                    purchaseOrderId, purchaseDetailRequest.getVariantId())) {
                throw new AppException(ErrorCode.PURCHASE_ORDER_PENDING);
            }

            Variant variant =
                    variantRepository
                            .findById(purchaseDetailRequest.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            PurchaseDetail purchaseDetail =
                    purchaseDetailMapper.createPurchaseDetail(purchaseDetailRequest);
            purchaseDetail.setVariant(variant);
            purchaseDetail.setPurchaseOrder(purchaseOrder);
            purchaseDetail.setSku(variant.getSku());

            purchaseDetailRepository.save(purchaseDetail);

            PurchaseOrderDetailResponse detailResponse =
                    PurchaseOrderDetailResponse.builder()
                            .variantId(purchaseDetail.getVariant().getId())
                            .quantity(purchaseDetail.getQuantity())
                            .purchasePrice(purchaseDetail.getPurchasePrice())
                            .build();
            purchaseOrderDetailResponses.add(detailResponse);
        }
        return CreatePurchaseOrderDetailResponse.builder()
                .purchaseOrderId(purchaseOrderId)
                .purchaseOrderDetails(purchaseOrderDetailResponses)
                .build();
    }

    @Override
    public PurchaseOrderResponse updatePurchaseOrder(
            Integer purchaseOrderId, UpdatePurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository
                        .findById(purchaseOrderId)
                        .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_NOT_FOUND));

        if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.COMPLETED.getValue())) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_COMPLETED);
        }

        purchaseOrderMapper.updatePurchaseOrder(purchaseOrder, request);
        purchaseOrderRepository.save(purchaseOrder);
        // Create inventory
        if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.COMPLETED.getValue())) {

            for (PurchaseDetail purchaseDetail : purchaseOrder.getPurchaseDetails()) {
                Inventory existingInventory =
                        inventoryRepository.findBySku(purchaseDetail.getSku()).orElse(null);
                if (existingInventory != null) {
                    existingInventory.setQuantity(
                            existingInventory.getQuantity() + purchaseDetail.getQuantity());
                    inventoryRepository.save(existingInventory);

                } else {
                    Inventory inventory =
                            Inventory.builder()
                                    .quantity(purchaseDetail.getQuantity())
                                    .variant(purchaseDetail.getVariant())
                                    .lastUpdated(LocalDateTime.now())
                                    .purchaseDetail(purchaseDetail)
                                    .location("Warehouse")
                                    .sku(purchaseDetail.getSku())
                                    .build();
                    inventoryRepository.save(inventory);
                }
                IncomingShipment incomingShipment =
                        IncomingShipment.builder()
                                .quantity(purchaseDetail.getQuantity())
                                .purchasePrice(purchaseDetail.getPurchasePrice())
                                .sku(purchaseDetail.getSku())
                                .variant(purchaseDetail.getVariant())
                                .purchaseOrder(purchaseOrder)
                                .build();

                incomingShipmentRepository.save(incomingShipment);
            }
        }
        return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }
}
