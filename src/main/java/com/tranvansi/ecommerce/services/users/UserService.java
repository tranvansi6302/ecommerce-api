package com.tranvansi.ecommerce.services.users;

import com.tranvansi.ecommerce.dtos.requests.addresses.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.addresses.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UpdateUserRequest;
import com.tranvansi.ecommerce.dtos.requests.users.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.addresses.AddressResponse;
import com.tranvansi.ecommerce.dtos.responses.users.ProfileResponse;
import com.tranvansi.ecommerce.dtos.responses.users.UserResponse;
import com.tranvansi.ecommerce.entities.Address;
import com.tranvansi.ecommerce.entities.Role;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.mappers.AddressMapper;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.AddressRepository;
import com.tranvansi.ecommerce.repositories.RoleRepository;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
    public void uploadProfileAvatar(UploadAvatarRequest request) throws IOException {
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
    public void uploadUserAvatar(Integer id, UploadAvatarRequest request) throws IOException {
        User user = userRepository.findById(id).orElseThrow(
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
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(user);
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
    public AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request) {

        Address address = addressRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        addressMapper.updateAddressDefault(address, request);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }


    @Override
    public Page<UserResponse> getAllUsers(PageRequest pageRequest, Specification<User> specification) {
        return userRepository.findAll(specification, pageRequest).map(userMapper::toUserResponse);
    }

    @Override
    public ProfileResponse getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toProfileResponse(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber()) &&
                (user.getPhoneNumber() == null || !user.getPhoneNumber().equals(request.getPhoneNumber()))) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail()) &&
                (user.getEmail() == null || !user.getEmail().equals(request.getEmail()))) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        userMapper.updateUser(user, request);
        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

}
