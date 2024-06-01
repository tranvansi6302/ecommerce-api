package com.tranvansi.ecommerce.modules.productmanagements.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IPricePlanService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IWarehouseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricePlanService implements IPricePlanService {
    private final PricePlanRepository pricePlanRepository;
    private final IWarehouseService warehouseService;
    private final IVariantService variantService;
    private final VariantMapper variantMapper;
    private final PricePlanMapper pricePlanMapper;

    @Override
    @Transactional
    public List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request) {
        List<PricePlanDetailResponse> createdPricePlans = new ArrayList<>();

        for (CreatePricePlanRequest.PricePlanRequest pricePlanRequest : request.getPricePlans()) {
            Variant variant = variantService.findVariantById(pricePlanRequest.getVariantId());
            if (warehouseService.existsByVariant(variant)) {
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

    @Override
    public Page<PricePlanDetailResponse> getHistoryPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification) {
        Page<PricePlan> pricePlans = pricePlanRepository.findAll(specification, pageRequest);
        List<PricePlanDetailResponse> pricePlanResponses = new ArrayList<>();
        for (PricePlan pricePlan : pricePlans) {
            VariantResponse variantResponse =
                    variantMapper.toVariantResponse(pricePlan.getVariant());
            PricePlanDetailResponse pricePlanResponse =
                    pricePlanMapper.toPricePlanDetailResponse(pricePlan);
            pricePlanResponse.setVariant(variantResponse);
            pricePlanResponses.add(pricePlanResponse);
        }
        return new PageImpl<>(pricePlanResponses, pageRequest, pricePlans.getTotalElements());
    }

    @Override
    public Page<PricePlanDetailResponse> getAllCurrentPricePlans(
            PageRequest pageRequest, Specification<PricePlan> specification) {

        List<PricePlan> pricePlans = pricePlanRepository.findAll(specification);
        List<PricePlanDetailResponse> pricePlanResponses = new ArrayList<>();
        Set<Integer> processedVariants = new HashSet<>();

        for (PricePlan pricePlan : pricePlans) {
            VariantResponse variantResponse =
                    variantMapper.toVariantResponse(pricePlan.getVariant());
            Integer variantId = variantResponse.getId();
            if (!processedVariants.contains(variantId)) {
                PricePlan checkPricePlan = getCurrentPricePlan(variantId);
                if (checkPricePlan != null) {
                    PricePlanDetailResponse pricePlanResponse =
                            pricePlanMapper.toPricePlanDetailResponse(checkPricePlan);
                    pricePlanResponse.setVariant(variantResponse);
                    pricePlanResponses.add(pricePlanResponse);
                    processedVariants.add(variantId);
                }
            }
        }

        return new PageImpl<>(pricePlanResponses, pageRequest, pricePlanResponses.size());
    }

    @Override
    public PricePlanDetailResponse updatePricePlan(
            Integer pricePlanId, UpdatePricePlanRequest request) {
        PricePlan pricePlan =
                pricePlanRepository
                        .findById(pricePlanId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRICE_PLAN_NOT_FOUND));

        List<PricePlan> existingPlans =
                pricePlanRepository.findByVariantIdOrderByStartDateDesc(
                        pricePlan.getVariant().getId());

        LocalDateTime newStartDate = request.getStartDate();
        LocalDateTime newEndDate = request.getEndDate();
        LocalDateTime now = LocalDateTime.now();

        for (PricePlan existingPlan : existingPlans) {
            if (!existingPlan.getId().equals(pricePlanId)) {
                if (newStartDate.isBefore(existingPlan.getEndDate())
                        && (newEndDate == null
                                || newEndDate.isAfter(existingPlan.getStartDate()))) {
                    throw new AppException(ErrorCode.PRICE_PLAN_START_DATE_INVALID);
                }
            }
        }

        if (newStartDate.isBefore(now)) {
            throw new AppException(ErrorCode.PRICE_PLAN_START_DATE_INVALID);
        }

        pricePlanMapper.updatePricePlan(pricePlan, request);

        if (request.getEndDate() == null) {
            for (PricePlan existingPlan : existingPlans) {
                if (!existingPlan.getId().equals(pricePlanId)
                        && existingPlan.getEndDate() == null) {
                    existingPlan.setEndDate(newStartDate);
                    pricePlanRepository.save(existingPlan);
                    break;
                }
            }
        }

        PricePlan updatedPricePlan = pricePlanRepository.save(pricePlan);

        VariantResponse variantResponse =
                variantMapper.toVariantResponse(updatedPricePlan.getVariant());
        PricePlanDetailResponse pricePlanResponse =
                pricePlanMapper.toPricePlanDetailResponse(updatedPricePlan);
        pricePlanResponse.setVariant(variantResponse);
        return pricePlanResponse;
    }

    private PricePlan getCurrentPricePlan(Integer variantId) {
        List<PricePlan> pricePlans =
                pricePlanRepository.findByVariantIdOrderByStartDateDesc(variantId);
        LocalDateTime now = LocalDateTime.now();

        for (PricePlan pricePlan : pricePlans) {
            if (pricePlan.getStartDate().isBefore(now)
                    && (pricePlan.getEndDate() == null || pricePlan.getEndDate().isAfter(now))) {
                return pricePlan;
            }
        }
        return null;
    }
}
