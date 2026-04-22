package com.example.Q.bucket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BucketResolver {

    @Value("${aws.s3.profileImageBucket")
    private String profileImageBucket;

    public String getBucketName(BucketName bucketName) {
        return switch (bucketName) {
            case PROFILE_IMAGE -> profileImageBucket;
        };
    }
}