package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.entities.Address;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.mappers.AddressMapper;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.AddressRepository;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber()) &&
                (user.getPhoneNumber() == null || !user.getPhoneNumber().equals(request.getPhoneNumber()))) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userMapper.updateProfile(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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

    @Override
    public AddressResponse createAddress(CreateAddressRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Address address = addressMapper.toAddress(request);
        address.setUser(user);
        address.setIsDefault(0);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddressDefault(String id, UpdateAddressDefaultRequest request) {

        Address address = addressRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        addressMapper.updateAddressDefault(address, request);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

}
