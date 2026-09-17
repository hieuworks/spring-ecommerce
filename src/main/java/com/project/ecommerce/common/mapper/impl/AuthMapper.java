package com.project.ecommerce.common.mapper.impl;

import com.project.ecommerce.auth.dto.RegisterRequest;
import com.project.ecommerce.common.mapper.Mapper;
import com.project.ecommerce.user.domain.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper implements Mapper<UserEntity, RegisterRequest> {
    private final PasswordEncoder passwordEncoder;

    public AuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity toEntity(RegisterRequest registerRequest) {
        return UserEntity.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password_hash(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .build();
    }

    @Override
    public RegisterRequest toDto(UserEntity userEntity) {
        return null;
    }
}
