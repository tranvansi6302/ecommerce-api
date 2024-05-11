package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.exceptions.ErrorCode;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setBlocked(false);
        return userMapper.toRegisterResponse(userRepository.save(user));
    }
}
