package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.entities.Address;
import com.tranvansi.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(CreateAddressRequest request);
    AddressResponse toAddressResponse(Address address);
    void updateAddressDefault(@MappingTarget Address address, UpdateAddressDefaultRequest request);
}
