package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.DeleteAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;

public interface IAddressService {
    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    Page<AddressResponse> getMyAddress(PageRequest pageRequest);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request);

    @PreAuthorize("hasRole('USER')")
    void deleteAddress(DeleteAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    AddressResponse getAddressById(Integer id);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddress(Integer id, UpdateAddressRequest request);
}
