package com.tranvansi.ecommerce.modules.suppliers.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.requests.UpdateStatusSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.requests.UpdateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;

public interface ISupplierService {
    @PreAuthorize("hasRole('ADMIN')")
    SupplierResponse createSupplier(CreateSupplierRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<SupplierResponse> getAllSuppliers(PageRequest pageRequest);

    @PreAuthorize("hasRole('ADMIN')")
    SupplierResponse getSupplierById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    SupplierResponse updateSupplier(Integer id, UpdateSupplierRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    SupplierResponse deleteSoftOrRestoreSupplier(Integer id, UpdateStatusSupplierRequest request);
}
