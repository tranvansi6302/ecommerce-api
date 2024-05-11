package com.tranvansi.ecommerce.mappers;

import com.tranvansi.ecommerce.dtos.requests.ForgotPasswordRequest;
import com.tranvansi.ecommerce.entities.ForgotToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForgotTokenMapper {
    ForgotToken toForgotToken(ForgotPasswordRequest request);
}
