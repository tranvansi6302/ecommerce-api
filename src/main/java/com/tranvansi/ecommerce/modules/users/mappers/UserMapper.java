package com.tranvansi.ecommerce.modules.users.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.requests.RegisterRequest;
import com.tranvansi.ecommerce.modules.users.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.modules.users.requests.UpdateUserRequest;
import com.tranvansi.ecommerce.modules.users.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.modules.users.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.users.responses.RegisterResponse;
import com.tranvansi.ecommerce.modules.users.responses.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User createUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);

    UserResponse toUserResponse(User user);

    ProfileResponse toProfileResponse(User user);

    @Mapping(target = "password", ignore = true)
    void updateProfile(@MappingTarget User user, UpdateProfileRequest request);

    void uploadAvatar(@MappingTarget User user, UploadAvatarRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
