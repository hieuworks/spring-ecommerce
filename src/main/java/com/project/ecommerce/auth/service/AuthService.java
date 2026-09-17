package com.project.ecommerce.auth.service;

import com.project.ecommerce.auth.dto.LoginRequest;
import com.project.ecommerce.auth.dto.LoginResponse;
import com.project.ecommerce.auth.dto.RegisterRequest;
import com.project.ecommerce.user.domain.dto.UserDto;

public interface AuthService {
    void create(RegisterRequest userDto);
    LoginResponse login(LoginRequest userDto);
}
