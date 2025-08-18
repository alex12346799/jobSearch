package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.dto.UserResponseDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.UserMapper;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.ImageService;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
//    private final UserDao userDao;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Пользователь с таким " + id + " не найден"));
    }
    @Override
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new NotFoundException("Пользователь с таким " + email + " не найден"));
        return UserMapper.toDto(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void register(UserRequestDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new NotFoundException("Имя обязательно");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new NotFoundException("Email  обязательно");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new NotFoundException("Пароль обязательно");
        }
        if (userRepository.existsByName(dto.getName())) {
            throw new NotFoundException("Пользователь с таким именем уже существует");
        }
        User existingUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new NotFoundException("Пользователь с таким email уже занят"));

        User user = UserMapper.fromDto(dto);
        user.setRole("Employer");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Пользователя с таким email не найден"));



        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Неверный пароль");
        }
        return user;
    }

    @Override
    public void updateUser(UserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new NotFoundException("Пользователь с таким email не найден"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        userRepository.save(user);
    }
@Override
public void uploadImageUser(MultipartFile file, String email) {
    User user = userRepository.findByEmail(email).orElseThrow(()->
            new NotFoundException("Пользователь не найден"));
    if (!file.isEmpty()) {
        String fileName = imageService.saveUploadedFile(file, "image");
        user.setAvatar(fileName);
    }
    userRepository.save(user);
    }


    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }


}
