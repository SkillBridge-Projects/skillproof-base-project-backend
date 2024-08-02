package com.skillproof.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            String fileName = file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (Exception ex) {
            LOG.error("Uploading file into AWS S3 bucket failed !!, {}", ex.getMessage());
            throw ex;
        }
    }

    public String getFileName(String fileUrl){
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
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

    public String generatePresignedUrl(String fileName) {
//        Date expiration = new Date();
//        long expTimeMillis = expiration.getTime();
//        expTimeMillis += 1000 * 60 * 60; // 1 hour
//        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public String getPresignedUrlForProfile(String profilePictureUrl) {
        LOG.debug("Start of getPresignedUrlForProfile method - UserServiceImpl");
        String preSignedUrl = null;
        if (StringUtils.isNotEmpty(profilePictureUrl)) {
            preSignedUrl = generatePresignedUrl(getFileName(profilePictureUrl));
        }
        return preSignedUrl;
    }
}

