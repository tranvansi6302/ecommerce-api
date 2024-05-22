package com.tranvansi.ecommerce.services.price_lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.dtos.requests.price_lists.CreatePriceListRequest;
import com.tranvansi.ecommerce.dtos.requests.price_lists.PriceListRequest;
import com.tranvansi.ecommerce.dtos.responses.price_lists.CreatePriceListResponse;
import com.tranvansi.ecommerce.dtos.responses.price_lists.PriceListResponse;
import com.tranvansi.ecommerce.entities.Inventory;
import com.tranvansi.ecommerce.entities.PriceList;
import com.tranvansi.ecommerce.entities.Variant;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.mappers.PriceListMapper;
import com.tranvansi.ecommerce.repositories.InventoryRepository;
import com.tranvansi.ecommerce.repositories.PriceListRepository;
import com.tranvansi.ecommerce.repositories.VariantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceListService implements IPriceListService {
    private final PriceListRepository priceListRepository;
    private final InventoryRepository inventoryRepository;
    private final VariantRepository repository;
    private final PriceListMapper priceListMapper;

    @Override
    public CreatePriceListResponse createPriceList(CreatePriceListRequest request) {
        List<PriceListResponse> priceListResponses = new ArrayList<>();

        for (PriceListRequest priceListRequest : request.getPriceLists()) {
            Variant variant =
                    repository
                            .findById(priceListRequest.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

            Inventory inventory =
                    inventoryRepository
                            .findByVariantIdAndSku(variant.getId(), variant.getSku())
                            .orElseThrow(
                                    () -> new AppException(ErrorCode.INVENTORY_NOT_FOUND_VARIANT));

            PriceList priceList = priceListMapper.createPriceList(priceListRequest);
            priceList.setVariant(variant);
            priceList.setEffectiveDate(LocalDateTime.now());
            priceList.setSku(inventory.getSku());

            priceListRepository.save(priceList);

            PriceListResponse priceListResponse =
                    PriceListResponse.builder()
                            .salePrice(priceList.getSalePrice())
                            .variantId(priceList.getVariant().getId())
                            .sku(priceList.getVariant().getSku())
                            .effectiveDate(priceList.getEffectiveDate())
                            .build();
            priceListResponses.add(priceListResponse);
        }
        return CreatePriceListResponse.builder().priceLists(priceListResponses).build();
    }
}
