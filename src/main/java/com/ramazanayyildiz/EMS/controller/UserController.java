package com.ramazanayyildiz.EMS.controller;

import com.ramazanayyildiz.EMS.dto.UserCreateDto;
import com.ramazanayyildiz.EMS.dto.UserUpdateDto;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserCreateDto userDto) {

        User newUser = userService.registerUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Erişim Başarılı! Bu korumalı bir alandır.");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto updateDto) {
        User updatedUser = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }
}
