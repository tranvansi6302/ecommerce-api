package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.dtos.requests.authentication.ForgotPasswordRequest;
import com.tranvansi.ecommerce.entities.ForgotToken;

@Mapper(componentModel = "spring")
public interface ForgotTokenMapper {
    ForgotToken toForgotToken(ForgotPasswordRequest request);
}
