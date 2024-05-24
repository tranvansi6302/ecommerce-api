package com.tranvansi.ecommerce.modules.users.services;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.modules.users.requests.UpdateUserRequest;
import com.tranvansi.ecommerce.modules.users.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.modules.users.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.users.responses.UserResponse;

public interface IUserService {
    @PreAuthorize("hasRole('USER')")
    UserResponse updateProfile(UpdateProfileRequest request);

    @PreAuthorize("hasRole('USER')")
    void uploadProfileAvatar(UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('ADMIN')")
    Page<UserResponse> getAllUsers(PageRequest pageRequest, Specification<User> specification);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ProfileResponse getProfile();

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUser(Integer id, UpdateUserRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void uploadUserAvatar(Integer id, UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(Integer id);
}
