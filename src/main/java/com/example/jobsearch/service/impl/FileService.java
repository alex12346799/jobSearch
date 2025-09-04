package com.example.jobsearch.service.impl;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
@Service
@Slf4j

public class FileService {
    private static final String UPLOAD_DIR = "data/images/image/";

//    @SneakyThrows
//    public ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
//        try {
//            byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + "/" + filename));
//            Resource resource = new ByteArrayResource(image);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                    .contentLength(resource.contentLength())
//                    .contentType(mediaType)
//                    .body(resource);
//        } catch (NoSuchFileException e) {
//            log.error("No file found:", e);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
//        }
//    }

    @SneakyThrows
    public ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
        try {

            java.nio.file.Path path = Paths.get(UPLOAD_DIR, filename);
            log.info("Reading file from: {}", path.toAbsolutePath());
            byte[] image = Files.readAllBytes(path);

            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            log.error("No file found: {}", filename, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }

}
