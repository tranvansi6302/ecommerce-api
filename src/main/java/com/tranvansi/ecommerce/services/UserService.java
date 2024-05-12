package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.exceptions.ErrorCode;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );
      if(userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }
        userMapper.updateProfile(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void uploadAvatar(UploadAvatarRequest request) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        if(user.getAvatar()==null) {
            userMapper.uploadAvatar(user, request);
        }else {
            Path oldAvatarPath = Paths.get("uploads", user.getAvatar());
            Files.deleteIfExists(oldAvatarPath);
            userMapper.uploadAvatar(user, request);
        }
        userRepository.save(user);
    }
}
