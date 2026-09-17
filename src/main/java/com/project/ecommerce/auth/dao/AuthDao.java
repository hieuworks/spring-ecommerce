package com.project.ecommerce.auth.dao;

import com.project.ecommerce.user.domain.entity.UserEntity;

public interface AuthDao {
    int create(UserEntity user);
}
