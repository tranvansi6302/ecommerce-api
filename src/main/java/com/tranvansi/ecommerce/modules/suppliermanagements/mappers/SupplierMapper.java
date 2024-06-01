package com.tranvansi.ecommerce.modules.suppliermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.suppliermanagements.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.requests.UpdateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliermanagements.responses.SupplierResponse;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier createSupplier(CreateSupplierRequest request);

    SupplierResponse toSupplierResponse(Supplier supplier);

    void updateSupplier(@MappingTarget Supplier supplier, UpdateSupplierRequest request);
}
