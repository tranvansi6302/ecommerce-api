package com.tranvansi.ecommerce.modules.usermanagements.services;

import java.io.IOException;
import java.util.List;

import com.tranvansi.ecommerce.modules.usermanagements.requests.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.services.AmazonClientService;
import com.tranvansi.ecommerce.components.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.usermanagements.entities.Role;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.mappers.UserMapper;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.RoleRepository;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.UserRepository;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthUtil authUtil;
    private final AmazonClientService amazonClientService;

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())
                && (user.getPhoneNumber() == null
                        || !user.getPhoneNumber().equals(request.getPhoneNumber()))) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }

        userMapper.updateProfile(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public ProfileResponse uploadAvatar(UploadAvatarRequest request) {
        User user = authUtil.getUser();
        String url = amazonClientService.uploadFile(request.getFile());
        user.setAvatar(url);
        return userMapper.toProfileResponse(userRepository.save(user));
    }

    @Override
    public ProfileResponse changePassword(ChangePasswordRequest request) {
        User user = authUtil.getUser();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CURRENT_INCORRECT);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return userMapper.toProfileResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteSoftManyUsers(DeleteSoftManyUserRequest request) {
        for (Integer id : request.getUserIds()) {
            User user =
                    userRepository
                            .findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            user.setIsDeleted(1);
            userRepository.save(user);
        }
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_OR_PASSWORD_INCORRECT));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(
            PageRequest pageRequest, Specification<User> specification) {
        return userRepository.findAll(specification, pageRequest).map(userMapper::toUserResponse);
    }

    @Override
    public ProfileResponse getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toProfileResponse(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())
                && (user.getPhoneNumber() == null
                        || !user.getPhoneNumber().equals(request.getPhoneNumber()))) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())
                && (user.getEmail() == null || !user.getEmail().equals(request.getEmail()))) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        userMapper.updateUser(user, request);
        List<Role> roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse uploadUserAvatar(Integer id, UploadAvatarRequest request)
            throws IOException {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String url = amazonClientService.uploadFile(request.getFile());
        user.setAvatar(url);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
