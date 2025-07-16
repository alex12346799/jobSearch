package com.example.jobsearch.controller;

import com.example.jobsearch.model.User;
import com.example.jobsearch.service.impl.UserServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
public final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
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
@GetMapping("/employer/{id}")
    public ResponseEntity<User> findEmployerId(@PathVariable int id) {
        Optional<User> employer = userService.findEmployerId(id);
        return employer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
}

}
