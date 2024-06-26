package com.tranvansi.ecommerce.components.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;

public class FileUtil {
    public static String storeImage(MultipartFile file) throws IOException {
        if (isImageFile(file) || file.getOriginalFilename() == null) {
            throw new AppException(ErrorCode.FILE_FORMAT_NOT_SUPPORTED);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = System.currentTimeMillis() + "-" + fileName;

        Path path = Paths.get("uploads");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path des = Paths.get(path + File.separator + uniqueFileName);
        Files.copy(
                file.getInputStream(),
                des,
                StandardCopyOption
                        .REPLACE_EXISTING); // StandardCopyOption.REPLACE_EXISTING ghi de neu ton
        // tai
        return uniqueFileName;
    }

    public static boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return false;
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("jpg")
                || extension.equals("jpeg")
                || extension.equals("png")
                || extension.equals("gif");
    }
}
