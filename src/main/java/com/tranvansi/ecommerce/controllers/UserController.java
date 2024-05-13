package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.*;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.filters.UserFilter;
import com.tranvansi.ecommerce.services.IUserService;
import com.tranvansi.ecommerce.specifications.UserSpecification;
import com.tranvansi.ecommerce.utils.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<ListUserResponse> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction,
           UserFilter filter
    ) {

        Sort sort = Sort.by("createdAt");
        sort = sort_direction.equalsIgnoreCase("asc")
                ? sort.ascending() : sort.descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<UserResponse> userResponses = userService.getAllUsers(pageRequest, new UserSpecification(filter));
        ListUserResponse response = ListUserResponse.builder()
                .result(userResponses.getContent())
                .pagination(PaginationResponse.builder()
                        .page(page)
                        .limit(limit)
                        .totalPage(userResponses.getTotalPages())
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
        return ResponseEntity.ok(response);
    }

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
        if (avatar.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_AVATAR_REQUIRED);
        }

        if (FileUtil.isImageFile(avatar)) {
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

    @PostMapping("/address")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @RequestBody @Valid CreateAddressRequest request) {
        AddressResponse addressResponse = userService.createAddress(request);
        ApiResponse<AddressResponse> response = ApiResponse.<AddressResponse>builder()
                .message(Message.CREATE_ADDRESS_SUCCESS.getMessage())
                .result(addressResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/address/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddressDefault(
            @PathVariable String id,
            @RequestBody @Valid UpdateAddressDefaultRequest request) {
        AddressResponse addressResponse = userService.updateAddressDefault(id, request);
        ApiResponse<AddressResponse> response = ApiResponse.<AddressResponse>builder()
                .message(Message.UPDATE_ADDRESS_DEFAULT_SUCCESS.getMessage())
                .result(addressResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        ProfileResponse profileResponse = userService.getProfile();
        ApiResponse<ProfileResponse> response = ApiResponse.<ProfileResponse>builder()
                .result(profileResponse)
                .build();
        return ResponseEntity.ok(response);
    }


}
