package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    @PreAuthorize("hasRole('USER')")
    UserResponse updateProfile(UpdateProfileRequest request);

    @PreAuthorize("hasRole('USER')")
    void uploadAvatar(UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(String id, UpdateAddressDefaultRequest request);

    @PreAuthorize("hasRole('ADMIN')")
     Page<UserResponse> getAllUsers(PageRequest pageRequest, Specification<User> specification);
}
