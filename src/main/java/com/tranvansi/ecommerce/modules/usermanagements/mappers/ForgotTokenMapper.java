package com.tranvansi.ecommerce.modules.usermanagements.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.usermanagements.entities.ForgotToken;
import com.tranvansi.ecommerce.modules.usermanagements.requests.ForgotPasswordRequest;

@Mapper(componentModel = "spring")
public interface ForgotTokenMapper {
    ForgotToken createForgotToken(ForgotPasswordRequest request);
}
