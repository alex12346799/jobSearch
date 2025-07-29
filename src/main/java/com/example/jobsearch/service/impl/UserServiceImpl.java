package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
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
    public void createUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userDao.save(user);

    }

    @Override
    public void updateUser(long id, UserDto dto) {
        User user = getUserById(id);
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setAvatar(dto.getAvatar());
        user.setAccountType(dto.getAccountType());
        userDao.update(user);
    }

    @Override
    public void deleteUser(long id) {
        getUserById(id);
        userDao.deleteById(id);
    }
}
