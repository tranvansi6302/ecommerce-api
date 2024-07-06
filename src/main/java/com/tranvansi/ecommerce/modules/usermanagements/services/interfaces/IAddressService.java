package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;

public interface IAddressService {
    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    Page<AddressResponse> getMyAddress(PageRequest pageRequest);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request);
}
