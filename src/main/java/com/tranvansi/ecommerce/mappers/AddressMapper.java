package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "isDefault", ignore = true)
    Address toAddress(CreateAddressRequest request);
    AddressResponse toAddressResponse(Address address);
}
