
package com.example.jobsearch.service.impl;

import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.FileService;
import com.example.jobsearch.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final UserRepository userRepository;
    private final ImageService imageService;

    private static final String IMAGE_DIR = "data/images";

    @Override
    public void uploadUserAvatar(MultipartFile file, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!file.isEmpty()) {
            String fileName = imageService.saveUploadedFile(file, "image");
            user.setAvatar(fileName);
            userRepository.save(user);
        }
    }


    @Override
    public ResponseEntity<?> downloadUserAvatar(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        String filename = user.getAvatar();
        if (filename == null || filename.isEmpty()) {
            filename = "picture.png";
        }

        return getOutputFile(filename, MediaType.IMAGE_PNG);
    }

    @Override
    public ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
        File file = new File(IMAGE_DIR, filename);
        log.info("🖼️ Попытка прочитать файл: {}", file.getAbsolutePath());

        if (!file.exists() || !file.isFile()) {
            log.warn("⚠️ Файл не найден: {}", filename);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found: " + filename);
        }

        Resource resource = new FileSystemResource(file);
        log.info("✅ Файл найден: {}", file.getAbsolutePath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("inline", filename);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}