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

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.productmanagements.entities.PricePlan;
import com.tranvansi.ecommerce.modules.productmanagements.entities.Variant;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.PricePlanMapper;
import com.tranvansi.ecommerce.modules.productmanagements.mappers.VariantMapper;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.PricePlanRepository;
import com.tranvansi.ecommerce.modules.productmanagements.repositories.ProductRepository;
import com.tranvansi.ecommerce.modules.productmanagements.requests.CreatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.requests.UpdatePricePlanRequest;
import com.tranvansi.ecommerce.modules.productmanagements.responses.PricePlanDetailResponse;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IPricePlanService;
import com.tranvansi.ecommerce.modules.productmanagements.services.interfaces.IVariantService;
import com.tranvansi.ecommerce.modules.salesmanagement.entities.ProductSale;
import com.tranvansi.ecommerce.modules.salesmanagement.repositories.ProductSaleRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.WarehouseRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.IWarehouseService;

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
    private final ProductSaleRepository productSaleRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional
    public List<PricePlanDetailResponse> createPricePlan(CreatePricePlanRequest request) {
        List<PricePlanDetailResponse> createdPricePlans = new ArrayList<>();

        for (CreatePricePlanRequest.PricePlanRequest pricePlanRequest : request.getPricePlans()) {
            Variant variant = variantService.findVariantById(pricePlanRequest.getVariantId());
            if (warehouseService.existsByVariant(variant)) {
                throw new AppException(ErrorCode.WAREHOUSE_VARIANT_NOT_FOUND);
            }

            if (pricePlanRequest.getStartDate() == null) {
                String errorRow = "Ngày bắt đầu không hợp lệ ở dòng : " + variant.getVariantName();
                throw new RuntimeException(errorRow);
            }
            Double originalPrice = null;
            if (pricePlanRequest.getPromotionPrice() != null) {
                var existingSalePricePlan =
                        pricePlanRepository.findByVariantIdAndEndDateIsNullOrderByStartDateDesc(
                                pricePlanRequest.getVariantId());
                if (!existingSalePricePlan.isEmpty()) {
                    originalPrice = existingSalePricePlan.getFirst().getSalePrice();
                } else {
                    originalPrice = pricePlanRequest.getSalePrice();
                }
                log.info("originalPrice: {}", originalPrice);

                PricePlan promotionPricePlan =
                        PricePlan.builder()
                                .variant(variant)
                                .salePrice(originalPrice)
                                .promotionPrice(pricePlanRequest.getPromotionPrice())
                                .discount(pricePlanRequest.getDiscount())
                                .status(pricePlanRequest.getStatus())
                                .startDate(pricePlanRequest.getStartDate())
                                .endDate(pricePlanRequest.getEndDate())
                                .build();

                PricePlan savedPromotionPricePlan = pricePlanRepository.save(promotionPricePlan);

                VariantResponse variantResponse = variantMapper.toVariantResponse(variant);
                savedPromotionPricePlan.setId(savedPromotionPricePlan.getId());
                PricePlanDetailResponse pricePlanResponse =
                        pricePlanMapper.toPricePlanDetailResponse(savedPromotionPricePlan);
                pricePlanResponse.setVariant(variantResponse);
                createdPricePlans.add(pricePlanResponse);
            }

            if (pricePlanRequest.getSalePrice() != null) {
                PricePlan salePricePlan =
                        PricePlan.builder()
                                .variant(variant)
                                .salePrice(pricePlanRequest.getSalePrice())
                                .promotionPrice(null)
                                .discount(pricePlanRequest.getDiscount())
                                .status(pricePlanRequest.getStatus())
                                .startDate(pricePlanRequest.getStartDate())
                                .endDate(null)
                                .build();

                PricePlan savedSalePricePlan = pricePlanRepository.save(salePricePlan);

                VariantResponse variantResponse = variantMapper.toVariantResponse(variant);
                savedSalePricePlan.setId(savedSalePricePlan.getId());
                PricePlanDetailResponse pricePlanResponse =
                        pricePlanMapper.toPricePlanDetailResponse(savedSalePricePlan);
                pricePlanResponse.setVariant(variantResponse);
                createdPricePlans.add(pricePlanResponse);
            }
            var product =
                    productRepository
                            .findById(variant.getProduct().getId())
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
            var warehouse =
                    warehouseRepository
                            .findByVariant(variant)
                            .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_FOUND));
            if (!productSaleRepository.existsByProductIdAndVariantId(
                    product.getId(), variant.getId())) {
                ProductSale productSale =
                        ProductSale.builder()
                                .product(product)
                                .variant(variant)
                                .warehouse(warehouse)
                                .build();
                productSaleRepository.save(productSale);
            }
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

        List<PricePlan> allPricePlans = pricePlanRepository.findAll(specification);
        List<PricePlanDetailResponse> pricePlanResponses = new ArrayList<>();
        Set<Integer> processedVariants = new HashSet<>();

        for (PricePlan pricePlan : allPricePlans) {
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
        // Pagination
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), pricePlanResponses.size());
        List<PricePlanDetailResponse> pagedResponses = pricePlanResponses.subList(start, end);

        return new PageImpl<>(pagedResponses, pageRequest, pricePlanResponses.size());
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

    @Override
    public List<PricePlan> findByVariantIdOrderByStartDateDesc(Integer variantId) {
        return pricePlanRepository.findByVariantIdOrderByStartDateDesc(variantId);
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
