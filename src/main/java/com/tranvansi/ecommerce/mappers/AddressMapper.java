package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.dtos.requests.addresses.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.addresses.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.responses.addresses.AddressResponse;
import com.tranvansi.ecommerce.entities.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address createAddress(CreateAddressRequest request);

    AddressResponse toAddressResponse(Address address);

    void updateAddressDefault(@MappingTarget Address address, UpdateAddressDefaultRequest request);
}
