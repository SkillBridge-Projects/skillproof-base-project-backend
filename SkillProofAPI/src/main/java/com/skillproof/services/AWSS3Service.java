package com.skillproof.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skillproof.services.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
public class AWSS3Service {

    private static final Logger LOG = LoggerFactory.getLogger(AWSS3Service.class);

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public AWSS3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucket}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) throws Exception {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (Exception ex) {
            LOG.error("Uploading file into AWS S3 bucket failed !!, {}", ex.getMessage());
            throw ex;
        }
    }

    public URL getFileUrl(String fileName) {
        return amazonS3.getUrl(bucketName, fileName);
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (Exception ex) {
            LOG.error("Deleting file from AWS S3 bucket failed !!, {}", ex.getMessage());
            throw ex;
        }
    }
}

