package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.ImageDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final UserRepository userRepository;
//    @Override
//    public String saveUploadedFile(MultipartFile file, String subDir) {
//        return ImageService.super.saveUploadedFile(file, subDir);
//    }
//
//    @Override
//    public ImageDto getById(long id) {
//        return null;
//    }
//
//    @Override
//    public void create(ImageDto imageDto){
//        String filename = saveUploadedFile(imageDto.getFile(), "images");
//        System.out.println(filename);
//    }
@Override
public String saveUploadedFile(MultipartFile file, String subDir) {
    return ImageService.super.saveUploadedFile(file, subDir);
}

    @Override
    public ImageDto getById(long id) {
        return null;
    }

    @Override
    public void create(ImageDto imageDto){
        String filename = saveUploadedFile(imageDto.getFile(), "images");
        System.out.println(filename);
    }
    @Override
    public void uploadImageUser(MultipartFile file, String email) {
    User user = userRepository.findByEmail(email).orElseThrow(()->
            new NotFoundException("User not found"));
    String filename = saveUploadedFile(file, "avatars");
    user.setAvatar(filename);
    userRepository.save(user);
    }

}
