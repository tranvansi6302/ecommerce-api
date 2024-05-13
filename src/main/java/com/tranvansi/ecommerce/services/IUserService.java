package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.*;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.ProfileResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

public interface IUserService {
    @PreAuthorize("hasRole('USER')")
    UserResponse updateProfile(UpdateProfileRequest request);

    @PreAuthorize("hasRole('USER')")
    void uploadProfileAvatar(UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('USER')")
    AddressResponse createAddress(CreateAddressRequest request);

    @PreAuthorize("hasRole('USER')")
    AddressResponse updateAddressDefault(String id, UpdateAddressDefaultRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<UserResponse> getAllUsers(PageRequest pageRequest, Specification<User> specification);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ProfileResponse getProfile();

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserById(String id);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUser(String id, UpdateUserRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void uploadUserAvatar(String id,UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(String id);
}
