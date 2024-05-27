package com.tranvansi.ecommerce.modules.pricePlans.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.pricePlans.entities.PricePlan;
import com.tranvansi.ecommerce.modules.pricePlans.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.pricePlans.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.pricePlans.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.pricePlans.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.products.entities.Variant;
import com.tranvansi.ecommerce.modules.products.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.products.repositories.VariantRepository;
import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.warehouses.repositories.WarehouseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricePlanService implements IPricePlanService {
    private final PricePlanRepository pricePlanRepository;
    private final WarehouseRepository warehouseRepository;
    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final PricePlanMapper pricePlanMapper;

    @Override
    @Transactional
    public List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request) {
        List<PricePlanDetailResponse> createdPricePlans = new ArrayList<>();

        for (CreatePricePlanRequest.PricePlanRequest pricePlanRequest : request.getPricePlans()) {
            Variant variant =
                    variantRepository
                            .findById(pricePlanRequest.getVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            if (!warehouseRepository.existsByVariant(variant)) {
                throw new AppException(ErrorCode.WAREHOUSE_VARIANT_NOT_FOUND);
            }
            PricePlan latestPlan = null;
            List<PricePlan> existingPlans =
                    pricePlanRepository.findByVariantIdAndEndDateIsNullOrderByStartDateDesc(
                            pricePlanRequest.getVariantId());
            if (!existingPlans.isEmpty()) {
                latestPlan = existingPlans.getFirst(); // Use get(0) for the first element
                if (pricePlanRequest.getStartDate().isEqual(latestPlan.getStartDate())
                        || pricePlanRequest.getStartDate().isBefore(latestPlan.getStartDate())) {
                    throw new AppException(ErrorCode.PRICE_PLAN_START_DATE_INVALID);
                }

                if (pricePlanRequest.getEndDate() == null) {
                    latestPlan.setEndDate(pricePlanRequest.getStartDate());
                    pricePlanRepository.save(latestPlan);
                }
            }

            PricePlan newPricePlan =
                    PricePlan.builder()
                            .variant(variant)
                            .salePrice(pricePlanRequest.getSalePrice())
                            .promotionPrice(pricePlanRequest.getPromotionPrice())
                            .discount(pricePlanRequest.getDiscount())
                            .status(pricePlanRequest.getStatus())
                            .startDate(pricePlanRequest.getStartDate())
                            .endDate(pricePlanRequest.getEndDate())
                            .build();
            if (pricePlanRequest.getEndDate() != null && latestPlan != null) {
                newPricePlan.setSalePrice(latestPlan.getSalePrice());
            }

            PricePlan savedPricePlan = pricePlanRepository.save(newPricePlan);

            VariantResponse variantResponse = variantMapper.toVariantResponse(variant);
            savedPricePlan.setId(savedPricePlan.getId());
            PricePlanDetailResponse pricePlanResponse =
                    pricePlanMapper.toPricePlanDetailResponse(savedPricePlan);
            pricePlanResponse.setVariant(variantResponse);
            createdPricePlans.add(pricePlanResponse);
        }
        return createdPricePlans;
    }
}
