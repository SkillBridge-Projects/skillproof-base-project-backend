package com.skillproof.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    public String getPresignedUrl(String profilePictureUrl) {
        LOG.debug("Start of getPresignedUrlForProfile method - UserServiceImpl");
        String preSignedUrl = null;
        if (StringUtils.isNotEmpty(profilePictureUrl)) {
            preSignedUrl = generatePresignedUrl(getFileName(profilePictureUrl));
        }
        return preSignedUrl;
    }

    public S3Object downloadFile(String fileName) throws Exception {
        S3Object s3Object = null;
        S3ObjectInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            // Get the S3 object from the bucket
            s3Object = amazonS3.getObject(bucketName, fileName);
            inputStream = s3Object.getObjectContent();

            // Define default destination path (temporary directory)
            String destinationPath = System.getProperty("java.io.tmpdir") + File.separator + fileName;

            // Create file and write to it
            File file = new File(destinationPath);
            outputStream = new FileOutputStream(file);

            byte[] readBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(readBuffer)) > 0) {
                outputStream.write(readBuffer, 0, bytesRead);
            }

            LOG.info("File downloaded successfully to: {}", destinationPath);
        } catch (Exception ex) {
            LOG.error("Downloading file from AWS S3 bucket failed !!, {}", ex.getMessage());
            throw ex;
        } finally {
            // Close all resources
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (s3Object != null) s3Object.close();
        }
        return s3Object;
    }
}

