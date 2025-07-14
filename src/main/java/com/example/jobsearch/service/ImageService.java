package com.example.jobsearch.service;

import com.example.jobsearch.dto.ImageDto;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface ImageService {
    @SneakyThrows
    default String saveUploadedFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + file.getOriginalFilename();
        Path pathDir = Paths.get("data/images/" + subDir);
        Files.createDirectories(pathDir);
        Path filePath = Paths.get(pathDir + "/" + fileName);
        if (!Files.exists(filePath)) Files.createFile(filePath);
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    ImageDto getById(long id);

    void create(ImageDto imageDto);
}
