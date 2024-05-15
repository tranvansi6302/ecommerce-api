package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.addresses.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.addresses.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.responses.addresses.AddressResponse;
import com.tranvansi.ecommerce.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(CreateAddressRequest request);
    AddressResponse toAddressResponse(Address address);
    void updateAddressDefault(@MappingTarget Address address, UpdateAddressDefaultRequest request);
}
