package com.project.ecommerce.user.controller;

import com.project.ecommerce.user.domain.dto.UserDto;
import com.project.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/api/v1/users/me")
    public ResponseEntity<String> me(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(user.getUsername());
    }

//    @PostMapping(path = "api/v1/auth/register")
//    public ResponseEntity<Void> create(@Valid @RequestBody UserDto request) {
//        userService.create(request);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
