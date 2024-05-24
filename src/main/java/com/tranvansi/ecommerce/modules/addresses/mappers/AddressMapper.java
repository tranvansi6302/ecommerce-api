package com.tranvansi.ecommerce.modules.addresses.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.addresses.entities.Address;
import com.tranvansi.ecommerce.modules.addresses.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.addresses.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.addresses.responses.AddressResponse;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address createAddress(CreateAddressRequest request);

    AddressResponse toAddressResponse(Address address);

    void updateAddressDefault(@MappingTarget Address address, UpdateAddressDefaultRequest request);
}
