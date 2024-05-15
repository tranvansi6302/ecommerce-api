package com.tranvansi.ecommerce.dtos.requests.users;

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
    @NotBlank(message = "INVALID_AVATAR_REQUIRED")
    private String avatar;
}
