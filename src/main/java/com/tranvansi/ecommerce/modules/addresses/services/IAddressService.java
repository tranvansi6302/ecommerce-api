package com.tranvansi.ecommerce.modules.addresses.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.addresses.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.addresses.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.addresses.responses.AddressResponse;

public interface IAddressService {
    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request);
}
