package com.ramazanayyildiz.EMS.controller;

import com.ramazanayyildiz.EMS.dto.UserCreateDto;
import com.ramazanayyildiz.EMS.dto.UserResponseDto;
import com.ramazanayyildiz.EMS.dto.UserUpdateDto;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserCreateDto userDto) {

        User newUser = userService.registerUser(userDto);
        return new ResponseEntity<>(userService.getUserById(newUser.getId()),HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Erişim Başarılı! Bu korumalı bir alandır.");
    }

    @GetMapping
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto updateDto) {
        UserResponseDto updatedUser = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();//204 dönderir işlem başarılı ama yanıt gövdesi boş
    }
}
