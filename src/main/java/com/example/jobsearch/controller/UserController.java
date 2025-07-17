package com.example.jobsearch.controller;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.impl.UserServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
public final UserServiceImpl userService;
public final UserDao userDao;

    public UserController(UserServiceImpl userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
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

@GetMapping
    public List<User> findAll() {
        return userDao.findAll();
}

@GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userDao.findByEmail(email);
}

@GetMapping("/name/{name}")
public List<User> getUserByName(@PathVariable String name) {
        return userDao.findByName(name);
}

@GetMapping("/phone/{phone}")
public List<User> getUserByPhone(@PathVariable String phone) {
        return userDao.findByPhone(phone);
}
@PutMapping("/{id}")
public ResponseEntity<User> update(@PathVariable long id, @RequestBody User user) {
       user.setId(id);
       userDao.update(user);
       return ResponseEntity.ok(user);
}

@PostMapping("/add")
    public void  crateUser(@RequestBody User user) {
        userDao.save(user);
}
@DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        userDao.delete(id);
}
}

