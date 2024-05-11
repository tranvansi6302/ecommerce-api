package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

    RegisterResponse toRegisterResponse(User user);
    UserResponse toUserResponse(User user);
}
