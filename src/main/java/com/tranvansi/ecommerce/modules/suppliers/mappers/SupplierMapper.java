package com.tranvansi.ecommerce.modules.suppliers.mappers;

import com.tranvansi.ecommerce.modules.suppliers.entities.Supplier;
import com.tranvansi.ecommerce.modules.suppliers.requests.CreateSupplierRequest;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier createSupplier(CreateSupplierRequest request);

    SupplierResponse toSupplierResponse(Supplier supplier);


}