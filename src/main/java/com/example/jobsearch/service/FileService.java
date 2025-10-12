package com.example.jobsearch.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void uploadUserAvatar(MultipartFile file, String email);

    ResponseEntity<?> downloadUserAvatar(long userId);

    ResponseEntity<?> getOutputFile(String filename, MediaType mediaType);
}
