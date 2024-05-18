package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.authentication.RegisterRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UpdateUserRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.users.ProfileResponse;
import com.tranvansi.ecommerce.dtos.responses.authentication.RegisterResponse;
import com.tranvansi.ecommerce.dtos.responses.users.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);


    UserResponse toUserResponse(User user);

    ProfileResponse toProfileResponse(User user);

    @Mapping(target = "password", ignore = true)
    void updateProfile(@MappingTarget User user, UpdateProfileRequest request);

    void uploadAvatar(@MappingTarget User user, UploadAvatarRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}

