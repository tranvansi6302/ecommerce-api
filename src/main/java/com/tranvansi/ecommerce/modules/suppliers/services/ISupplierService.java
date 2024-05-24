package com.tranvansi.ecommerce.modules.suppliers.services;

import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ISupplierService {
    @PreAuthorize("hasRole('ADMIN')")
    SupplierResponse createSupplier(CreateSupplierRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<SupplierResponse> getAllSuppliers(PageRequest pageRequest);

}
