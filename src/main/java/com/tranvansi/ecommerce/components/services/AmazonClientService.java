package com.tranvansi.ecommerce.components.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class AmazonClientService {
    private AmazonS3 s3Client;
    private TransferManager transferManager;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client =
                AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(region)
                        .build();
        this.transferManager =
                TransferManagerBuilder.standard()
                        .withS3Client(s3Client)
                        .withExecutorFactory(() -> Executors.newFixedThreadPool(5))
                        .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile =
                new File(
                        System.getProperty("java.io.tmpdir")
                                + "/"
                                + Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        String originalFileName = Objects.requireNonNull(multiPart.getOriginalFilename());
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        return new Date().getTime() + "-" + baseName.replace(" ", "_") + ".jpg";
    }

    private void uploadFileTos3bucket(String fileName, File file) throws InterruptedException {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        Upload upload = transferManager.upload(request);
        upload.waitForCompletion();
    }

    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        List<String> fileUrls = new ArrayList<>();
        if (multipartFiles.size() > 5) {
            throw new IllegalArgumentException("Không thể tải lên quá 5 tệp cùng một lúc");
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String fileUrl = uploadFile(multipartFile);
            if (!fileUrl.isEmpty()) {
                fileUrls.add(fileUrl);
            }
        }
        return fileUrls;
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        File originalFile = null;
        File convertedFile = null;
        try {
            // Convert MultipartFile to File
            originalFile = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);

            // Create temporary file for resized image
            convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

           

            // Upload converted file to S3
            uploadFileTos3bucket(fileName, convertedFile);
            fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            log.error("Không thể upload file: {}", e.getMessage());
        } finally {
            // Delete temporary files
            if (originalFile != null && !originalFile.delete()) {
                log.error("Không thể xóa file gốc: {}", originalFile.getAbsolutePath());
            }
            if (convertedFile != null && !convertedFile.delete()) {
                log.error("Không thể xóa file đã chuyển đổi: {}", convertedFile.getAbsolutePath());
            }
        }
        return fileUrl;
    }
    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(bucketName, fileName);
    }

}