package com.example.jobsearch.controller;

import com.example.jobsearch.dto1.ImageDto;
import com.example.jobsearch.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("images")
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
}
