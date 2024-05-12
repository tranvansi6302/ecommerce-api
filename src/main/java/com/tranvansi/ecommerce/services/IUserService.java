package com.tranvansi.ecommerce.services;

import com.tranvansi.ecommerce.dtos.requests.UpdateProfileRequest;
import com.tranvansi.ecommerce.dtos.requests.UploadAvatarRequest;
import com.tranvansi.ecommerce.dtos.responses.UserResponse;

import java.io.IOException;

public interface IUserService {
    UserResponse updateProfile(UpdateProfileRequest request);
    void uploadAvatar(UploadAvatarRequest request) throws IOException;
}
