package com.tranvansi.ecommerce.modules.suppliermanagements.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Warehouse;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.WarehouseRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseDetail;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.PurchaseOrder;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliermanagements.mappers.PurchaseOrderMapper;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.PurchaseDetailRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.PurchaseOrderRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.SupplierRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdatePurchaseOrderRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IPurchaseOrderService;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.UserRepository;

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
            if(purchaseDetailRequest.getQuantity() <= 0) {
                throw new AppException(ErrorCode.INVALID_PURCHASE_DETAIL_QUANTITY);
            }
            if(purchaseDetailRequest.getPurchasePrice() <= 0) {
                throw new AppException(ErrorCode.INVALID_PURCHASE_DETAIL_PURCHASE_PRICE);
            }
            PurchaseDetail purchaseDetail =
                    PurchaseDetail.builder()
                            .purchaseOrder(savedPurchaseOrder)
                            .quantity(purchaseDetailRequest.getQuantity())
                            .variant(variant)
                            .purchasePrice(purchaseDetailRequest.getPurchasePrice())
                            .quantityReceived(0)
                            .sku(variant.getSku())
                            .note(purchaseDetailRequest.getNote())
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

        if(purchaseOrder.getStatus().equals(PurchaseOrderStatus.COMPLETED)) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_NOT_UPDATE);

        }
        if(purchaseOrder.getStatus().equals(PurchaseOrderStatus.CANCELLED)) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_NOT_UPDATE);
        }

        purchaseOrder.setStatus(request.getStatus());
        purchaseOrder.setCancelReason(request.getCancelReason());
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
                purchaseDetail.setNote(detailRequest.getNote());
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

    @Override
    public Page<PurchaseOrderResponse> getAllPurchaseOrders(PageRequest pageRequest, Specification<PurchaseOrder> specification) {
        return purchaseOrderRepository.findAll(specification, pageRequest).map(purchaseOrderMapper::toPurchaseOrderResponse);
    }

    @Override
    public PurchaseOrderResponse getPurchaseOrderById(Integer purchaseOrderId) {
        return purchaseOrderRepository.findById(purchaseOrderId)
                .map(purchaseOrderMapper::toPurchaseOrderResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));
    }
}
