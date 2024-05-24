package com.tranvansi.ecommerce.modules.suppliers.services;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliers.mappers.SupplierMapper;
import com.tranvansi.ecommerce.modules.suppliers.repositories.SupplierRepository;
import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponse createSupplier(CreateSupplierRequest request) {
        checkExistingSupplier(request);
        Supplier supplier = supplierMapper.createSupplier(request);
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
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
