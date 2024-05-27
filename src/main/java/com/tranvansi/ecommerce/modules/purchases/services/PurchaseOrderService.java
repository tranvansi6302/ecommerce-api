package com.tranvansi.ecommerce.modules.purchases.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseDetail;
import com.tranvansi.ecommerce.modules.purchases.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.purchases.mappers.PurchaseOrderMapper;
import com.tranvansi.ecommerce.modules.purchases.repositories.PurchaseDetailRepository;
import com.tranvansi.ecommerce.modules.purchases.repositories.PurchaseOrderRepository;
import com.tranvansi.ecommerce.modules.purchases.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliers.repositories.SupplierRepository;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;
import com.tranvansi.ecommerce.modules.warehouses.entities.Warehouse;
import com.tranvansi.ecommerce.modules.warehouses.repositories.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final VariantRepository variantRepository;
    private final WarehouseRepository warehouseRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request) {
        if (purchaseOrderRepository.existsByPurchaseOrderCode(request.getPurchaseOrderCode())) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_CODE_ALREADY_EXISTS);
        }
        Supplier supplier =
                supplierRepository
                        .findById(request.getSupplierId())
                        .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PurchaseOrder purchaseOrder = purchaseOrderMapper.createPurchaseOrder(request);
        purchaseOrder.setPurchaseOrderDate(LocalDateTime.now());
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setUser(user);
        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        List<PurchaseDetail> purchaseDetails = new ArrayList<>();

        for (CreatePurchaseOrderRequest.PurchaseDetailRequest purchaseDetailRequest :
                request.getPurchaseDetails()) {
            Variant variant =
                    variantRepository
                            .findById(purchaseDetailRequest.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            PurchaseDetail purchaseDetail =
                    PurchaseDetail.builder()
                            .purchaseOrder(savedPurchaseOrder)
                            .quantity(purchaseDetailRequest.getQuantity())
                            .variant(variant)
                            .purchasePrice(purchaseDetailRequest.getPurchasePrice())
                            .quantityReceived(0)
                            .sku(variant.getSku())
                            .build();
            purchaseDetails.add(purchaseDetail);
        }
        savedPurchaseOrder.setPurchaseDetails(purchaseDetails);
        purchaseDetailRepository.saveAll(purchaseDetails);

        return purchaseOrderMapper.toPurchaseOrderResponse(savedPurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse updatePurchaseOrder(
            Integer purchaseOrderId, UpdatePurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository
                        .findById(purchaseOrderId)
                        .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));

        purchaseOrder.setStatus(request.getStatus());
        if (request.getStatus().equals(PurchaseOrderStatus.COMPLETED)) {
            for (UpdatePurchaseOrderRequest.PurchaseDetailUpdateRequest detailRequest :
                    request.getPurchaseDetails()) {
                PurchaseDetail purchaseDetail =
                        purchaseDetailRepository
                                .findByPurchaseOrderAndVariantId(
                                        purchaseOrder, detailRequest.getVariantId())
                                .orElseThrow(
                                        () ->
                                                new AppException(
                                                        ErrorCode.PURCHASE_DETAIL_NOT_FOUND));
                if (detailRequest.getQuantityReceived() > purchaseDetail.getQuantity()) {
                    throw new AppException(ErrorCode.QUANTITY_RECEIVED_GREATER_THAN_PURCHASED);
                }
                purchaseDetail.setQuantityReceived(detailRequest.getQuantityReceived());

                purchaseDetailRepository.save(purchaseDetail);
            }

            for (PurchaseDetail purchaseDetail : purchaseOrder.getPurchaseDetails()) {
                Warehouse warehouse =
                        warehouseRepository
                                .findByVariantAndSku(
                                        purchaseDetail.getVariant(), purchaseDetail.getSku())
                                .orElse(null);

                if (warehouse != null) {
                    warehouse.setTotalQuantity(
                            warehouse.getTotalQuantity() + purchaseDetail.getQuantity());
                    warehouse.setAvailableQuantity(
                            warehouse.getAvailableQuantity() + purchaseDetail.getQuantity());
                    warehouse.setLastUpdated(LocalDateTime.now());
                    warehouse.setPurchasePrice(purchaseDetail.getPurchasePrice());
                } else {
                    warehouse =
                            Warehouse.builder()
                                    .variant(purchaseDetail.getVariant())
                                    .sku(purchaseDetail.getSku())
                                    .availableQuantity(purchaseDetail.getQuantity())
                                    .totalQuantity(purchaseDetail.getQuantity())
                                    .purchasePrice(purchaseDetail.getPurchasePrice())
                                    .lastUpdated(LocalDateTime.now())
                                    .build();
                }
                warehouseRepository.save(warehouse);
            }
        }

        purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }
}
