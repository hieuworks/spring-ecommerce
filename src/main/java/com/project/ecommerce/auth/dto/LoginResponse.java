package com.project.ecommerce.auth.dto;

import com.project.ecommerce.user.domain.dto.UserDto;

public record LoginResponse(String accessToken, String tokenType, long expiresIn, UserDto user) {
}
