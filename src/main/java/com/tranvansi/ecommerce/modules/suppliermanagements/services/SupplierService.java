package com.tranvansi.ecommerce.modules.suppliermanagements.services;

import com.tranvansi.ecommerce.modules.suppliermanagements.requests.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.SupplierStatus;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliermanagements.mappers.SupplierMapper;
import com.tranvansi.ecommerce.modules.suppliermanagements.repositories.SupplierRepository;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.SupplierResponse;
import com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces.ISupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponse createSupplier(CreateSupplierRequest request) {
        checkExistingSupplier(request);
        Supplier supplier = supplierMapper.createSupplier(request);
        supplier.setStatus(SupplierStatus.ACTIVE);
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public Page<SupplierResponse> getAllSuppliers(
            PageRequest pageRequest, Specification<Supplier> specification) {
        return supplierRepository
                .findAll(specification, pageRequest)
                .map(supplierMapper::toSupplierResponse);
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        return supplierMapper.toSupplierResponse(
                supplierRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND)));
    }

    @Override
    public SupplierResponse updateSupplier(Integer id, UpdateSupplierRequest request) {

        Supplier supplier =
                supplierRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        if (supplierRepository.existsByName(request.getName())
                && !supplier.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.SUPPLIER_NAME_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByTaxCode(request.getTaxCode())
                && !supplier.getTaxCode().equals(request.getTaxCode())) {
            throw new AppException(ErrorCode.SUPPLIER_TAX_CODE_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByEmail(request.getEmail())
                && !supplier.getEmail().equals(request.getEmail())) {
            throw new AppException(ErrorCode.SUPPLIER_EMAIL_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByPhoneNumber(request.getPhoneNumber())
                && !supplier.getPhoneNumber().equals(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS);
        }
        supplierMapper.updateSupplier(supplier, request);
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse deleteSoftOrRestoreSupplier(
            Integer id, UpdateStatusSupplierRequest request) {
        Supplier supplier =
                supplierRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        supplier.setStatus(request.getStatus());
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public void updateManyStatusSupplier(UpdateManyStatusSupplierRequest request) {
        for (Integer id : request.getSupplierIds()) {
            Supplier supplier =
                    supplierRepository
                            .findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
            if(supplier.getStatus().equals(SupplierStatus.ACTIVE)){
                  supplier.setStatus(SupplierStatus.INACTIVE);
            }else {
                    supplier.setStatus(SupplierStatus.ACTIVE);
            }

            supplierRepository.save(supplier);
        }
    }

    @Override
    public void deleteManySuppliers(DeleteManySupplierRequest request) {
        for (Integer id : request.getSupplierIds()) {
            Supplier supplier =
                    supplierRepository
                            .findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
            supplierRepository.delete(supplier);
        }
    }

    private void checkExistingSupplier(CreateSupplierRequest request) {
        if (supplierRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.SUPPLIER_NAME_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByTaxCode(request.getTaxCode())) {
            throw new AppException(ErrorCode.SUPPLIER_TAX_CODE_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.SUPPLIER_EMAIL_ALREADY_EXISTS);
        }
        if (supplierRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS);
        }
    }
}
