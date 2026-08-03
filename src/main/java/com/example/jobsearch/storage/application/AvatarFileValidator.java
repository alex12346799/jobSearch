package com.example.jobsearch.storage.application;

import com.example.jobsearch.storage.infrastructure.MinioStorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AvatarFileValidator {
    private static final byte[] PNG = {(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
    private static final Set<String> ALLOWED = Set.of("image/jpeg", "image/png");
    private final MinioStorageProperties properties;

    public ValidatedAvatar validate(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new AvatarValidationException("Avatar file must not be empty");
        if (file.getSize() > properties.avatarMaxSize()) throw new AvatarTooLargeException();
        if (!ALLOWED.contains(file.getContentType())) throw new AvatarValidationException("Only JPEG and PNG images are allowed");
        try {
            byte[] bytes = file.getBytes();
            String detected = detect(bytes);
            if (!file.getContentType().equals(detected)) throw new AvatarValidationException("Image content does not match Content-Type");
            var image = ImageIO.read(new ByteArrayInputStream(bytes));
            if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
                throw new AvatarValidationException("File content is not a valid image");
            }
            return new ValidatedAvatar(bytes, detected);
        } catch (IOException exception) {
            throw new AvatarValidationException("File content is not a valid image");
        }
    }

    private String detect(byte[] bytes) {
        if (bytes.length >= PNG.length && Arrays.equals(Arrays.copyOf(bytes, PNG.length), PNG)) return "image/png";
        if (bytes.length >= 3 && (bytes[0] & 0xff) == 0xff && (bytes[1] & 0xff) == 0xd8
                && (bytes[2] & 0xff) == 0xff) return "image/jpeg";
        throw new AvatarValidationException("File content is not a JPEG or PNG image");
    }

    public record ValidatedAvatar(byte[] content, String contentType) {}
}
