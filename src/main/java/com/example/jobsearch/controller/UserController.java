package com.example.jobsearch.controller;

import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String redisterUser(@RequestBody User user) {
    User saved = userService.register(user);
    if ("APPLICANT".equals(saved.getRole())) {
        return "Вы зарегистрировали как сосискатель работы";
    } else if ("EMPLOYER".equalsIgnoreCase(saved.getRole())) {
        return "Вы зарегистрировали как работадатель";
        
    }else {
        return "Неизвестная роль. Можете выбрать между APPLICANT или EMPLOYER";
    }
}

}
