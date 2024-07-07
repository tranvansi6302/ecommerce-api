package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.requests.ChangePasswordRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateUserRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;

public interface IUserService {
    @PreAuthorize("hasRole('USER')")
    UserResponse updateProfile(UpdateProfileRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    Page<UserResponse> getAllUsers(PageRequest pageRequest, Specification<User> specification);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ProfileResponse getProfile();

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUserById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse updateUser(Integer id, UpdateUserRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse uploadUserAvatar(Integer id, UploadAvatarRequest request) throws IOException;

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(Integer id);

    @PreAuthorize("hasRole('USER')")
    ProfileResponse uploadAvatar(UploadAvatarRequest request);

    @PreAuthorize("hasRole('USER')")
    ProfileResponse changePassword(ChangePasswordRequest request);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);

    User saveUser(User user);
}
