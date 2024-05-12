package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

public interface IUserService {
    @PreAuthorize("hasRole('USER')")
    UserResponse updateProfile(UpdateProfileRequest request);

    @PreAuthorize("hasRole('USER')")
    void uploadAvatar(UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);
}
