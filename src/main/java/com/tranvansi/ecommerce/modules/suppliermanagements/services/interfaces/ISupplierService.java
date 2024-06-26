package com.tranvansi.ecommerce.modules.suppliermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdateStatusSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.SupplierResponse;

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
