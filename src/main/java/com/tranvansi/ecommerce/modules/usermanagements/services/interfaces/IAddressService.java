package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;

public interface IAddressService {
    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request);
}
