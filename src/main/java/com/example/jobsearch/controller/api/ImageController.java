package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.ImageDto;
import com.example.jobsearch.service.ImageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImageController {
private final ImageService imageService;

@GetMapping("{id}")
public ImageDto getImage(@PathVariable long id) {
    return imageService.getById(id);
}
@PostMapping
    public HttpStatus create( ImageDto imageDto) {
    imageService.create(imageDto);
    return HttpStatus.CREATED;
}

//    @GetMapping("download/{userId}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable long userId) {
//    return ResponseEntity.ok()
//    }
}
