package com.tranvansi.ecommerce.modules.purchases.services;

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
import com.tranvansi.ecommerce.modules.purchases.responses.PurchaseOrderResponse;
import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliers.repositories.SupplierRepository;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final VariantRepository variantRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request) {
        if(purchaseOrderRepository.existsByPurchaseOrderCode(request.getPurchaseOrderCode())) {
            throw new AppException(ErrorCode.PURCHASE_ORDER_CODE_ALREADY_EXISTS);
        }
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PurchaseOrder purchaseOrder = purchaseOrderMapper.createPurchaseOrder(request);
        purchaseOrder.setPurchaseOrderDate(LocalDateTime.now());
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setUser(user);
        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        List<PurchaseDetail> purchaseDetails = new ArrayList<>();

        for (CreatePurchaseOrderRequest.PurchaseDetailRequest purchaseDetailRequest : request.getPurchaseDetails()) {
            Variant variant = variantRepository.findById(purchaseDetailRequest.getVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            PurchaseDetail purchaseDetail = PurchaseDetail.builder()
                    .purchaseOrder(savedPurchaseOrder)
                    .quantity(purchaseDetailRequest.getQuantity())
                    .variant(variant)
                    .sku(variant.getSku())
                    .build();
            purchaseDetails.add(purchaseDetail);
        }
        savedPurchaseOrder.setPurchaseDetails(purchaseDetails);
        purchaseDetailRepository.saveAll(purchaseDetails);

        return purchaseOrderMapper.toPurchaseOrderResponse(savedPurchaseOrder);
    }
}
