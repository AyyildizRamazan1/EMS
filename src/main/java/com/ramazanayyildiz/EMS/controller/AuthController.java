package com.ramazanayyildiz.EMS.controller;

import com.ramazanayyildiz.EMS.dto.LoginDto;
import com.ramazanayyildiz.EMS.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){
        String jwtToken = authService.login(loginDto);
        return ResponseEntity.ok(jwtToken);
    }


}
