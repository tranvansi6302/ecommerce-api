package com.tranvansi.ecommerce.modules.suppliers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.requests.UpdateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier createSupplier(CreateSupplierRequest request);

    SupplierResponse toSupplierResponse(Supplier supplier);

    void updateSupplier(@MappingTarget Supplier supplier, UpdateSupplierRequest request);
}
