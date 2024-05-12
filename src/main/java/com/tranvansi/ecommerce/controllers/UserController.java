package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.services.IUserService;
import com.tranvansi.ecommerce.utils.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @RequestBody @Valid UpdateProfileRequest request) {
        UserResponse updateProfileResponse = userService.updateProfile(request);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message(Message.UPDATE_PROFILE_SUCCESS.getMessage())
                .result(updateProfileResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/profile/upload")
    public ResponseEntity<ApiResponse<String>> uploadAvatar(
            @ModelAttribute("avatar") MultipartFile avatar) throws IOException {
        if(avatar.isEmpty()) {
           throw new AppException(ErrorCode.INVALID_AVATAR_REQUIRED);
        }

        if(FileUtil.isImageFile(avatar)) {
            throw new AppException(ErrorCode.INVALID_AVATAR_FORMAT);
        }
        if (avatar.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new AppException(ErrorCode.FILE_SIZE_TOO_LARGE);
        }
        String avatarImg = FileUtil.storeImage(avatar);
        UploadAvatarRequest request = UploadAvatarRequest.builder()
                .avatar(avatarImg)
                .build();
        userService.uploadAvatar(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(Message.UPLOAD_AVATAR_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }


}
