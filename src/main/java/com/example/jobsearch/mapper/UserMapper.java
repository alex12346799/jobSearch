package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.UserCreateDto;
import com.example.jobsearch.model.User;

public class UserMapper {
    public static User fromDto(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setSurname(userCreateDto.getSurname());
        user.setPassword(userCreateDto.getPassword());
        user.setAddress(userCreateDto.getAddress());
        user.setAvatar(userCreateDto.getAvatar());
        user.setEmail(userCreateDto.getEmail());
        user.setEmail(userCreateDto.getEmail());
        user.setAge(user.getAge());
        return user;
    }
}
