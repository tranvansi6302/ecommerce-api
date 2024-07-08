package com.tranvansi.ecommerce.modules.usermanagements.controllers;

import java.io.IOException;
import java.util.List;

import com.tranvansi.ecommerce.modules.usermanagements.requests.*;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tranvansi.ecommerce.components.constants.FileConstant;
import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.components.utils.FileUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.usermanagements.filters.UserFilter;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ProfileResponse;
import com.tranvansi.ecommerce.modules.usermanagements.responses.UserResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IUserService;
import com.tranvansi.ecommerce.modules.usermanagements.specifications.UserSpecification;

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
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "is_deleted", required = false) Integer isDeleted,
            @RequestParam(name = "email", required = false) String email,


            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        UserFilter filter = UserFilter.builder().status(status).email(email).isDeleted(isDeleted).build();
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
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

    @PatchMapping(value = "/profile/upload")
    public ResponseEntity<ApiResponse<ProfileResponse>> uploadProfileAvatar(
            @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_REQUIRED);
        }

        if (!FileUtil.isImageFile(file)) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_FORMAT);
        }
        if (file.getSize() > FileConstant.MAX_FILE_SIZE_MB) { // 5MB
            throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
        UploadAvatarRequest request = UploadAvatarRequest.builder().file(file).build();
        ProfileResponse profileResponse = userService.uploadAvatar(request);
        ApiResponse<ProfileResponse> response =
                ApiResponse.<ProfileResponse>builder()
                        .message(Message.UPLOAD_AVATAR_SUCCESS.getMessage())
                        .result(profileResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<ApiResponse<UserResponse>> uploadUserAvatar(
            @PathVariable Integer id, @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_REQUIRED);
        }

        if (!FileUtil.isImageFile(file)) {
            throw new AppException(ErrorCode.INVALID_USER_AVATAR_FORMAT);
        }
        if (file.getSize() > FileConstant.MAX_FILE_SIZE_MB) { // 5MB
            throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
        UploadAvatarRequest request = UploadAvatarRequest.builder().file(file).build();
        UserResponse userResponse = userService.uploadUserAvatar(id, request);
        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .message(Message.UPLOAD_AVATAR_SUCCESS.getMessage())
                        .result(userResponse)
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

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<ProfileResponse>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request) {
        ProfileResponse profileResponse = userService.changePassword(request);
        ApiResponse<ProfileResponse> response =
                ApiResponse.<ProfileResponse>builder()
                        .message(Message.CHANGE_PASSWORD_SUCCESS.getMessage())
                        .result(profileResponse)
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<String>> deleteSoftManyUsers(
            @RequestBody @Valid DeleteSoftManyUserRequest request) {
        userService.deleteSoftManyUsers(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_SOFT_USER_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/restore")
    public ResponseEntity<ApiResponse<String>> restoreManyUsers(
            @RequestBody @Valid RestoreManyUserRequest request) {
        userService.restoreManyUsers(request);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.RESTORE_USER_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
