package com.example.jobsearch.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
public class ImageDto {
    private MultipartFile file;

}
