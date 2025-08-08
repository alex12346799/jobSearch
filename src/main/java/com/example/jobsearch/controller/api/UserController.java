package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.UserRequestDto;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDto dto) {
        userService.createUser(dto);
        return ResponseEntity.ok("Пользователь упешно создан! ");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserRequestDto userDto) {
        userService.updateUser(id, userDto);
        User updatedUser = userService.getUserById(id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
