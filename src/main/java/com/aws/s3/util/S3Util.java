package com.aws.s3.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class S3Util {

    private static final AwsCredentialsProvider credentialsProvider = createCredentialsProvider();
    private static final Region AWS_REGION = Region.of(System.getenv("AWS_REGION"));
    private static final String AWS_BUCKET = System.getenv("AWS_BUCKET");
    public static final String AWS_URL_FOLDER = System.getenv("AWS_URL_FOLDER");

    private S3Util() {
        throw new IllegalStateException("Utility class");
    }

    private static AwsCredentialsProvider createCredentialsProvider() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY"));
        return StaticCredentialsProvider.create(awsCredentials);
    }

    public static void uploadFile(String fileName, InputStream inputStream)
            throws AwsServiceException, SdkClientException, IOException {
        try (S3Client client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(AWS_REGION)
                .build()) {
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(AWS_BUCKET)
                    .key(fileName)
                    .acl("public-read")
                    .build();
            client.putObject(req, RequestBody.fromInputStream(inputStream, inputStream.available()));
        }
    }

    public static void deleteFile(String fileName)
            throws AwsServiceException, SdkClientException {
        try (S3Client client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(AWS_REGION)
                .build()) {
            DeleteObjectRequest req = DeleteObjectRequest.builder()
                    .bucket(AWS_BUCKET)
                    .key(fileName)
                    .build();
            client.deleteObject(req);
        }
    }
}
