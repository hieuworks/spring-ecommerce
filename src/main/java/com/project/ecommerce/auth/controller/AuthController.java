package com.project.ecommerce.auth.controller;

import com.project.ecommerce.auth.dto.RegisterRequest;
import com.project.ecommerce.auth.dto.LoginRequest;
import com.project.ecommerce.auth.dto.LoginResponse;
import com.project.ecommerce.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping(path = "/api/v1/auth/register")
    public ResponseEntity<Void> create(@Valid @RequestBody RegisterRequest request) {
        authService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/api/v1/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
