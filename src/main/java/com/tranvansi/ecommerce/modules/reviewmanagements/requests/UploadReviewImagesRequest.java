package com.tranvansi.ecommerce.modules.reviewmanagements.requests;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadReviewImagesRequest {
    @NotNull(message = "INVALID_IMAGE_URLS_REQUIRED")
    private List<MultipartFile> files;
}
