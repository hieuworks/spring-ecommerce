package com.project.ecommerce.user.userDao;

import com.project.ecommerce.user.domain.entity.UserEntity;

import java.util.Optional;

public interface UserDao {
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    int create(UserEntity user);
}
