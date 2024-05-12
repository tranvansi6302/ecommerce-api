package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);
    UserResponse toUserResponse(User user);

    void updateProfile(@MappingTarget User user, UpdateProfileRequest request);
    void uploadAvatar(@MappingTarget User user, UploadAvatarRequest request);
}
