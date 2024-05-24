package com.tranvansi.ecommerce.modules.users.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.common.utils.FileUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.users.filters.UserFilter;
import com.tranvansi.ecommerce.modules.users.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.modules.users.requests.UpdateUserRequest;
import com.tranvansi.ecommerce.modules.users.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.modules.users.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.users.responses.UserResponse;
import com.tranvansi.ecommerce.modules.users.services.IUserService;
import com.tranvansi.ecommerce.modules.users.specifications.UserSpecification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction,
            UserFilter filter) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<UserResponse> userResponses =
                userService.getAllUsers(pageRequest, new UserSpecification(filter));
        PagedResponse<List<UserResponse>> response =
                BuildResponse.buildPagedResponse(userResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder().result(userResponse).build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Integer id, @RequestBody @Valid UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .message(Message.UPDATE_USER_SUCCESS.getMessage())
                        .result(userResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @RequestBody @Valid UpdateProfileRequest request) {
        UserResponse updateProfileResponse = userService.updateProfile(request);
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .message(Message.UPDATE_PROFILE_SUCCESS.getMessage())
                        .result(updateProfileResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile/upload")
    public ResponseEntity<ApiResponse<String>> uploadProfileAvatar(
            @ModelAttribute("avatar") MultipartFile avatar) throws IOException {
        if (avatar.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_REQUIRED);
        }

        if (FileUtil.isImageFile(avatar)) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_FORMAT);
        }
        if (avatar.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
        String avatarImg = FileUtil.storeImage(avatar);
        UploadAvatarRequest request = UploadAvatarRequest.builder().avatar(avatarImg).build();
        userService.uploadProfileAvatar(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.UPLOAD_AVATAR_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<ApiResponse<String>> uploadUserAvatar(
            @PathVariable Integer id, @ModelAttribute("avatar") MultipartFile avatar)
            throws IOException {
        if (avatar.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_REQUIRED);
        }

        if (FileUtil.isImageFile(avatar)) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_FORMAT);
        }
        if (avatar.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
        String avatarImg = FileUtil.storeImage(avatar);
        UploadAvatarRequest request = UploadAvatarRequest.builder().avatar(avatarImg).build();
        userService.uploadUserAvatar(id, request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.UPLOAD_AVATAR_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        ProfileResponse profileResponse = userService.getProfile();
        ApiResponse<ProfileResponse> response =
                ApiResponse.<ProfileResponse>builder().result(profileResponse).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_USER_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
