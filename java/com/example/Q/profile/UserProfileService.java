package com.example.Q.profile;

import com.example.Q.bucket.BucketName;
import com.example.Q.bucket.BucketResolver;
import com.example.Q.datastore.UserProfileRepository;
import com.example.Q.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileStore fileStore;
    private final BucketResolver bucketResolver;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository,
                              FileStore fileStore,
                              BucketResolver bucketResolver) {
        this.userProfileRepository = userProfileRepository;
        this.fileStore = fileStore;
        this.bucketResolver = bucketResolver;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }


    @Transactional
    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {

        isFileEmpty(file);
        isImage(file);

        UserProfile user = getUserProfileOrThrow(userProfileId);

        Map<String, String> metadata = extractMetadata(file);

        String bucketName = bucketResolver.getBucketName(BucketName.PROFILE_IMAGE);
        String path = user.getUserProfileId().toString();

        String filename = String.format("%s-%s",
                file.getOriginalFilename(),
                UUID.randomUUID());

        try {
            fileStore.save(
                    bucketName,
                    path,
                    filename,
                    Optional.of(metadata),
                    file.getInputStream()
            );

            user.setUserProfileImageLink(filename);
            userProfileRepository.save(user);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {

        UserProfile user = getUserProfileOrThrow(userProfileId);

        String bucketName = bucketResolver.getBucketName(BucketName.PROFILE_IMAGE);
        String path = user.getUserProfileId().toString();

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(bucketName, path, key))
                .orElse(new byte[0]);
    }


    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new IllegalStateException(
                        "User profile " + userProfileId + " not found"
                ));
    }

    private void isImage(MultipartFile file) {
        if (file.getContentType() == null ||
                !Arrays.asList(
                        IMAGE_JPEG.getMimeType(),
                        IMAGE_PNG.getMimeType(),
                        IMAGE_GIF.getMimeType()
                ).contains(file.getContentType())) {
            throw new IllegalStateException(
                    "File must be an image [" + file.getContentType() + "]"
            );
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot upload empty file [" + file.getSize() + "]"
            );
        }
    }
}