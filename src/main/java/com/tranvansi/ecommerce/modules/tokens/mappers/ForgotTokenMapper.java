package com.tranvansi.ecommerce.modules.tokens.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.tokens.entities.ForgotToken;
import com.tranvansi.ecommerce.modules.users.requests.ForgotPasswordRequest;

@Mapper(componentModel = "spring")
public interface ForgotTokenMapper {
    ForgotToken createForgotToken(ForgotPasswordRequest request);
}
