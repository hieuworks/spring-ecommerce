package com.project.ecommerce.common.mapper.impl;

import com.project.ecommerce.common.mapper.Mapper;
import com.project.ecommerce.user.domain.dto.UserDto;
import com.project.ecommerce.user.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, UserDto> {

    @Override
    public UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .status(userDto.getStatus())
                .roles(userDto.getRoles())
                .build();
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .roles(userEntity.getRoles())
                .build();
    }
}
