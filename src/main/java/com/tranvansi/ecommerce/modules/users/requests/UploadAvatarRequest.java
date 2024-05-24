package com.tranvansi.ecommerce.modules.users.requests;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadAvatarRequest {
    @NotBlank(message = "INVALID_USER_AVATAR_REQUIRED")
    private String avatar;
}
