package com.project.ecommerce.auth.service.impl;

import com.project.ecommerce.auth.dto.LoginRequest;
import com.project.ecommerce.auth.dto.LoginResponse;
import com.project.ecommerce.auth.dto.RegisterRequest;
import com.project.ecommerce.auth.exception.EmailAlreadyRegisteredException;
import com.project.ecommerce.auth.exception.InvalidCredentialsException;
import com.project.ecommerce.auth.service.AuthService;
import com.project.ecommerce.auth.service.JwtService;
import com.project.ecommerce.common.mapper.Mapper;
import com.project.ecommerce.user.domain.dto.UserDto;
import com.project.ecommerce.user.domain.entity.UserEntity;
import com.project.ecommerce.user.exception.UserRegistrationFailedException;
import com.project.ecommerce.user.service.UserService;
import com.project.ecommerce.user.userDao.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final Mapper<UserEntity, RegisterRequest> mapper;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final JwtService jwtService;

    public AuthServiceImpl(UserDao userDao, AuthenticationManager authenticationManager,
                           Mapper<UserEntity, RegisterRequest> mapper,
                           Mapper<UserEntity, UserDto> userMapper, JwtService jwtService,
                           UserService userService) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void create(RegisterRequest request) {
        if (userDao.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }
        UserEntity userEntity = mapper.toEntity(request);
        userService.create(userEntity);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword())
            );
        } catch (org.springframework.security.core.AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }

        UserEntity user = userDao.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);
        return new LoginResponse(jwtService.generateToken(user), "Bearer", jwtService.getExpiresInSeconds(), userMapper.toDto(user));
    }
}
