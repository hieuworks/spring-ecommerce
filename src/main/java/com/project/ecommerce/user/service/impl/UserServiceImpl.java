package com.project.ecommerce.user.service.impl;

import com.project.ecommerce.common.mapper.Mapper;
import com.project.ecommerce.user.domain.dto.UserDto;
import com.project.ecommerce.user.domain.entity.UserEntity;
import com.project.ecommerce.user.exception.UserRegistrationFailedException;
import com.project.ecommerce.user.service.UserService;
import com.project.ecommerce.user.userDao.jdbc.JdbcUserDao;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final Mapper<UserEntity, UserDto> mapper;
    private final JdbcUserDao jdbcUserDao;

    public UserServiceImpl(Mapper<UserEntity, UserDto> mapper, JdbcUserDao jdbcUserDao) {
        this.mapper = mapper;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public void create(UserEntity userEntity) {
        if(jdbcUserDao.create(userEntity)!=1){
            throw new UserRegistrationFailedException();
        }
    }
}
