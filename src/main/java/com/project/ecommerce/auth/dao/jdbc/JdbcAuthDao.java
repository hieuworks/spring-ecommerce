package com.project.ecommerce.auth.dao.jdbc;

import com.project.ecommerce.auth.dao.AuthDao;
import com.project.ecommerce.user.domain.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAuthDao implements AuthDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(UserEntity user) {
        String sql = """
                INSERT INTO users (name, email, password_hash)
                VALUES (?, ?, ?)
                """;
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword_hash());
    }
}

