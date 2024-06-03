package com.tranvansi.ecommerce.modules.usermanagements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.requests.RegisterRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateUserRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.RegisterResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User createUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);

    UserResponse toUserResponse(User user);

    ProfileResponse toProfileResponse(User user);

    @Mapping(target = "password", ignore = true)
    void updateProfile(@MappingTarget User user, UpdateProfileRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
