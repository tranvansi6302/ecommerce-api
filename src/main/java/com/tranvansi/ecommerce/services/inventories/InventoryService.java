package com.tranvansi.ecommerce.services.inventories;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryService implements IInventoryService {
    //    private final InventoryRepository inventoryRepository;
    //    private final VariantRepository variantRepository;
    //    private final PurchaseOrderRepository purchaseOrderRepository;
    //    private final PurchaseDetailRepository purchaseDetailRepository;
    //    private final InventoryMapper inventoryMapper;
    //
    //    @Override
    //    public void createInventory(CreateInventoryRequest request) {
    //        PurchaseDetail purchaseDetail = purchaseDetailRepository.
    //                findById(request.getPurchaseDetailId()).orElseThrow(
    //                        () -> new AppException(ErrorCode.PURCHASE_DETAIL_NOT_FOUND)
    //                );
    //
    //        Inventory inventory = inventoryMapper.createInventory(request);
    //        inventory.setVariant(purchaseDetail.getVariant());
    //        inventory.setPurchaseDetail(purchaseDetail);
    //        inventory.setSku(purchaseDetail.getSku());
    //        inventory.setQuantity(purchaseDetail.getQuantity());
    //        inventoryRepository.save(inventory);
    //
    //
    //    }
}
