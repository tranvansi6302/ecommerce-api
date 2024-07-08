package com.tranvansi.ecommerce.modules.usermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.usermanagements.entities.Address;
import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address createAddress(CreateAddressRequest request);

    void updateAddress(@MappingTarget Address address, UpdateAddressRequest request);

    AddressResponse toAddressResponse(Address address);

    void updateAddressDefault(@MappingTarget Address address, UpdateAddressDefaultRequest request);
}
