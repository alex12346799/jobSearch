package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.UserMapper;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User getUserById(Long id) {
        return userDao.findById(id).
                orElseThrow(() -> new NotFoundException("Пользователь с таким " + id + " не найден"));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
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
        if (userDao.findByName(dto.getName())) {
            throw new NotFoundException("Пользователь с таким именем уже существует");
        }
        User existingUser = userDao.findByEmail(dto.getEmail());
        if (existingUser != null) {
            throw new NotFoundException("Пользователь с таким email уже занят");
        }
        User user = UserMapper.fromDto(dto);
        user.setRole("Employer");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);

    }

    @Override
    public User login(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Пользователя с таким email не найден");
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Неверный пароль");
        }
        return user;
    }

    @Override
    public void updateUser(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        userDao.update(user);
    }


    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userDao.deleteById(id);
    }


}
