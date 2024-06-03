package com.tranvansi.ecommerce.modules.productmanagements.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadProductImagesRequest {
    @NotBlank(message = "INVALID_USER_AVATAR_REQUIRED")
    private List<MultipartFile> files;
}
