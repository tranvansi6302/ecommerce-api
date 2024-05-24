package com.tranvansi.ecommerce.modules.suppliers.services;

import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;

public interface ISupplierService {
    SupplierResponse createSupplier(CreateSupplierRequest request);
}
